package com.synechron.usermanagement.service;

import com.synechron.usermanagement.auth.service.JwtService;
import com.synechron.usermanagement.dto.*;
import com.synechron.usermanagement.dto.kafka.PolicyUserIdRequestMessage;
import com.synechron.usermanagement.dto.kafka.PolicyUserIdResponseMessage;
import com.synechron.usermanagement.mapper.*;
import com.synechron.usermanagement.model.User;
import com.synechron.usermanagement.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.messaging.MessagingException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private KafkaTemplate<String, byte[]> kafkaTemplateUser;
    private JsonDeserializer<PolicyUserIdRequestMessage> policyUserIdRequestMessageDeserializer = new JsonDeserializer<>(PolicyUserIdRequestMessage.class);
    private JsonSerializer<PolicyUserIdResponseMessage> policyUserIdResponseMessageSerializer = new JsonSerializer<>();

    private final String USER_REQUEST_TOPIC = "user.request.dobar1";
    private final String USER_RESPONSE_TOPIC = "user.response111";
    private String newEmail;
    @Autowired
    private JwtService jwtService;

    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AddressService addressService;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private ContactService contactService;
    @Autowired
    private ContactMapper contactMapper;
    @Autowired
    private UserRegisterMapper registerMapper;

    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private SubscriberMapper subscriberMapper;

    public List<User> getAll() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
 }


    @KafkaListener(topics = USER_REQUEST_TOPIC, groupId = "user-service-group")
    public void processUserEmailRequest(byte[] requestData) {
        PolicyUserIdRequestMessage requestMessage = policyUserIdRequestMessageDeserializer.deserialize(USER_REQUEST_TOPIC, requestData);
        String email = requestMessage.getEmail();
        User user = repository.findByEmail(email);
        kafkaTemplateUser.send(USER_RESPONSE_TOPIC,  policyUserIdResponseMessageSerializer.serialize(USER_RESPONSE_TOPIC, new PolicyUserIdResponseMessage(requestMessage.getCorrelationId(), user.getId(),1,10)));
    }

    public UserResponseDTO getByEmail(String email) {
        try {
            User user = repository.findByEmail(email);
            return UserMapper.toResponseDTO(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while retrieving the user by email: " + email);
        }
    }

    public UserResponseDTO update(Long userId, UserProfileUpdateDTO userProfileUpdateDTO) throws IOException {
        User user = repository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
        user.setFirstName(userProfileUpdateDTO.getFirstName());
        user.setLastName(userProfileUpdateDTO.getLastName());
        user.setJmbg(userProfileUpdateDTO.getJmbg());
        user.setBirth(userProfileUpdateDTO.getBirth());
        user.setGender(userProfileUpdateDTO.getGender());
        user.setMaritialStatus(userProfileUpdateDTO.getMaritialStatus());

        MultipartFile profileImage = userProfileUpdateDTO.getProfileImage();
        if (profileImage != null && !profileImage.isEmpty()) {
            String fileExtension = Objects.requireNonNull(profileImage.getOriginalFilename()).split("\\.")[1];
            String storedFileName = "user_" + System.currentTimeMillis() + "." + fileExtension;
            Path uploadDir = Paths.get("../user-management-service/src/main/resources/uploads/images");
            Path targetPath = uploadDir.resolve(storedFileName);

            Files.createDirectories(uploadDir);
            Files.write(targetPath, profileImage.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            user.setProfileImage(storedFileName);
        }

        User savedUser = repository.save(user);
        return userMapper.toResponseDTO(savedUser);
    }

    public User register(UserRegisterDTO dto) throws MessagingException {
        try{
            dto.setIsActive(false);
            if (repository.existsByEmail(dto.getEmail())) {
                throw new IllegalStateException("Email already in use.");
            }
            User mappedUser = registerMapper.toEntity(dto);
            AddressResponseDTO addressDto = addressService.getById(dto.getAddress());
            ContactResponseDTO contactDto = contactService.create(dto.getContact());
            mappedUser.setAddress(addressMapper.toEntity(addressDto));
            mappedUser.setContact(contactMapper.toResponseEntity(contactDto));
            mappedUser.setPassword(passwordEncoder.encode(dto.getPassword()));
            if(dto.getIsEnabled()){
                mappedUser.setIsActive(true);
            }

            User user = repository.save(mappedUser);
//            if(dto.getIsEnabled()){
//
//                emailSenderService.sendVerificationEmail(user.getEmail()); port ne funkcionise
//            }

            return user;
        } catch(Exception e){
            System.out.println(e);
            throw e;
        }
    }
}






