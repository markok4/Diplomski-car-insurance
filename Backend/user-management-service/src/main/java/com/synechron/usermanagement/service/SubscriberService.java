package com.synechron.usermanagement.service;

import com.synechron.usermanagement.dto.*;
import com.synechron.usermanagement.dto.kafka.PolicySearchSubscriberRequestMessage;
import com.synechron.usermanagement.dto.kafka.PolicySearchSubscriberResponseMessage;
import com.synechron.usermanagement.dto.kafka.ProposalSubscriberValidMessage;
import com.synechron.usermanagement.mapper.AddressMapper;
import com.synechron.usermanagement.mapper.ContactMapper;
import com.synechron.usermanagement.mapper.SubscriberMapper;
import com.synechron.usermanagement.mapper.UserRegisterMapper;
import com.synechron.usermanagement.model.Subscriber;
import com.synechron.usermanagement.model.SubscriberRole;
import com.synechron.usermanagement.model.User;
import com.synechron.usermanagement.model.UserRole;
import com.synechron.usermanagement.repository.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriberService {
    @Autowired
    private SubscriberRepository repository;

    @Autowired
    private SubscriberRoleService subscriberRoleService;
    @Autowired
    private SubscriberMapper subscriberMapper;
    @Autowired
    private UserRegisterMapper registerMapper;
    @Autowired
    private AddressService addressService;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private ContactService contactService;
    @Autowired
    private ContactMapper contactMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private KafkaTemplate<String, byte[]> kafkaTemplate;

    private final String IS_SUBSCRIBER_VALID_REQUEST_TOPIC = "subscriber.validity.request";
    private final String IS_SUBSCRIBER_VALID_RESPONSE_TOPIC = "subscriber.validity.response";
    private final String SUBSCRIBER_REQUEST_TOPIC = "policy.subscriber.request";
    private final String SUBSCRIBER_RESPONSE_TOPIC = "policy.subscriber.response";
    private JsonDeserializer<PolicySearchSubscriberRequestMessage> requestMessageDeserializer = new JsonDeserializer<>(PolicySearchSubscriberRequestMessage.class);
    private JsonSerializer<PolicySearchSubscriberResponseMessage> responseMessageSerializer = new JsonSerializer<>();
    private JsonDeserializer<ProposalSubscriberValidMessage> proposalSubscriberValidMessageJsonDeserializer = new JsonDeserializer<>(ProposalSubscriberValidMessage.class);
    private JsonSerializer<ProposalSubscriberValidMessage> proposalSubscriberValidMessageJsonSerializer = new JsonSerializer<>();


    public Page<SubscriberResponseDTO> getAll(Pageable pageable) {
        try {
            Page<Subscriber> subscriberPage = repository.findAll(pageable);
            return subscriberPage.map(sub -> subscriberMapper.toResponseDTO(sub));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public SubscriberResponseDTO getById(Long id) {
        try {
            Subscriber subscriber = repository.getReferenceById(id);
            return subscriberMapper.toResponseDTO(subscriber); } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    public SubscriberResponseDTO register(SubscriberRegisterDTO dto) {
        try {
            if (repository.existsByEmail(dto.getEmail())) {
                throw new IllegalStateException("Email already in use.");
            }
            Subscriber mappedUser = registerMapper.toEntity(dto);
            AddressResponseDTO addressDto = addressService.getById(dto.getAddress());
            ContactResponseDTO contactDto = contactService.create(dto.getContact());
            mappedUser.setAddress(addressMapper.toEntity(addressDto));
            mappedUser.setContact(contactMapper.toResponseEntity(contactDto));
            mappedUser.setUserRole(UserRole.SUBSCRIBER);
            mappedUser.setSubscriberRole(subscriberRoleService.getById(dto.getSubscriberRoleId()));
            mappedUser.setIsDeleted(false);
            mappedUser.setIsActive(false);
            mappedUser.setPassword(passwordEncoder.encode(dto.getPassword()));

            Subscriber user = repository.save(mappedUser);

            return subscriberMapper.toResponseDTO(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<SubscriberRoleDTO> getRoles() {
        try {
            List<SubscriberRole> roles = subscriberRoleService.getAll();
            return roles.stream().map(role -> subscriberMapper.roleToDTO(role)).toList();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    @KafkaListener(topics = SUBSCRIBER_REQUEST_TOPIC, groupId = "user-service-group")
    public void handleSubscriberRequest(byte[] requestData) {
        PolicySearchSubscriberRequestMessage requestMessage = requestMessageDeserializer.deserialize(SUBSCRIBER_REQUEST_TOPIC, requestData);

        String nameOrEmail = requestMessage.getNameOrEmail();

        List<Subscriber> subscribers = repository.findBySearchTerm(nameOrEmail);

        List<Long> subscriberIds = subscribers.stream().map(Subscriber::getId).collect(Collectors.toList());

        kafkaTemplate.send(SUBSCRIBER_RESPONSE_TOPIC, responseMessageSerializer.serialize(SUBSCRIBER_RESPONSE_TOPIC, new PolicySearchSubscriberResponseMessage(requestMessage.getCorrelationId(), subscriberIds)));
    }

    @KafkaListener(topics = IS_SUBSCRIBER_VALID_REQUEST_TOPIC, groupId = "user-service-group")
    public void handleIsSubscriberValidRequst(byte[] requestData) {
        ProposalSubscriberValidMessage responseMessage = proposalSubscriberValidMessageJsonDeserializer.deserialize(IS_SUBSCRIBER_VALID_REQUEST_TOPIC, requestData);
        Boolean result = repository.existsById(responseMessage.getSubscriberId());
        if (!result) {
            responseMessage.setSubscriberId(-1l);
        }
        kafkaTemplate.send(IS_SUBSCRIBER_VALID_RESPONSE_TOPIC, proposalSubscriberValidMessageJsonSerializer.serialize(IS_SUBSCRIBER_VALID_RESPONSE_TOPIC, responseMessage));
    }

    public List<SubscriberResponseDTO> search(int page, int size, String credentials) {

        if (credentials == null || credentials.isEmpty()) {
            return subscriberMapper.toResponseDTOList(repository.findAll());
        }
        Pageable pageable = PageRequest.of(page, size);

        Page<Subscriber> searchResults = repository.searchByCredentials(credentials, pageable);

        return subscriberMapper.toResponseDTOList(searchResults.getContent());
    }
}
