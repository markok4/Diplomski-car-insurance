package com.synechron.carservice.service;

import com.synechron.carservice.dto.CarCreateDTO;
import com.synechron.carservice.dto.CarDTO;
import com.synechron.carservice.dto.CarDTO2;
import com.synechron.carservice.dto.CarResponseDTO;
import com.synechron.carservice.dto.kafka.PolicySearchCarRequestMessage;
import com.synechron.carservice.dto.kafka.PolicySearchCarResponseMessage;
import com.synechron.carservice.mapper.CarDTO2Mapper;
import com.synechron.carservice.mapper.CarDTOMapper;
import com.synechron.carservice.mapper.CarResponseDTOMapper;
import com.synechron.carservice.model.Car;
import com.synechron.carservice.model.CarPart;
import com.synechron.carservice.repository.CarRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarPartService carPartService;
    @Autowired
    private ModelService modelService;
    //    TODO IMATE 3 MAPPERA
    @Autowired
    private CarResponseDTOMapper carResponseDtoMapper;
    @Autowired
    private CarDTOMapper carDtoMapper;
    @Autowired
    private CarDTO2Mapper carDTO2Mapper;

    @Autowired
    private KafkaTemplate<String, byte[]> kafkaTemplate;

    private final String CAR_REQUEST_TOPIC = "policy.car.request";
    private final String CAR_RESPONSE_TOPIC = "policy.car.response";
    private JsonDeserializer<PolicySearchCarRequestMessage> requestMessageDeserializer = new JsonDeserializer<>(PolicySearchCarRequestMessage.class);
    private JsonSerializer<PolicySearchCarResponseMessage> responseMessageSerializer = new JsonSerializer<>();

    public Page<CarDTO> getAll(Pageable pageable) {
        try {
            Page<Car> carPage = carRepository.findByIsDeletedFalse(pageable);
            List<CarDTO> carDtoList = carDtoMapper.listToDTO(carPage.getContent());
            return new PageImpl<>(carDtoList, pageable, carPage.getTotalElements());
        } catch (Exception e) {
            throw e;
        }
    }

    public CarDTO getById(Long id){
        try{
            Optional<Car> result = carRepository.findById(id);
            if (result.isPresent()) {
                return carResponseDtoMapper.toDTO(result.get());
            } else {
                throw new EntityNotFoundException("Entity does not exist");
            }
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    public void delete(Long id) {
        Optional<Car> result = carRepository.findById(id);
        if (result.isPresent()) {
            result.get().setIsDeleted(true);
            carRepository.save(result.get());
        } else {
            // Throw a custom exception or handle the not found case
            throw new EntityNotFoundException("Entity with id " + id + " does not exist");
        }
    }

    public void restore(Long id) {
        Optional<Car> result = carRepository.findById(id);
        if (result.isPresent()) {
            result.get().setIsDeleted(false);
            carRepository.save(result.get());
        } else {
            // Throw a custom exception or handle the not found case
            throw new EntityNotFoundException("Entity with id " + id + " does not exist");
        }
    }

    public CarDTO2 create(CarCreateDTO carCreateDTO) throws IOException {

        Set<CarPart> carParts = new HashSet<CarPart>();
        Arrays.stream(carCreateDTO.getCarPartIds()).forEach(id -> carParts.add(carPartService.getById(id)));

        Car newCar = Car.builder()
                .year(carCreateDTO.getYear())
                .model(modelService.getReferenceById(carCreateDTO.getModelId()))
                .carParts(carParts)
                .isDeleted(false)
                .build();

        if (carCreateDTO.getImage() != null && !carCreateDTO.getImage().isEmpty()) {
            String fileExtension = Objects.requireNonNull(carCreateDTO.getImage().getOriginalFilename()).split("\\.")[1];
            String storedFileName = "car_" + System.currentTimeMillis() + "." + fileExtension;
            Path uploadDir = Paths.get("../car-service/src/main/resources/uploads/cars");
            Path targetPath = uploadDir.resolve(storedFileName);

            Files.createDirectories(uploadDir);
            Files.write(targetPath, carCreateDTO.getImage().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            newCar.setImage(storedFileName);
        }

        Car savedCar = carRepository.save(newCar);
        return carDTO2Mapper.toCarDTO(savedCar);
    }

    @KafkaListener(topics = CAR_REQUEST_TOPIC, groupId = "car-service-group")
    public void handleSubscriberRequest(byte[] requestData) {
        PolicySearchCarRequestMessage requestMessage = requestMessageDeserializer.deserialize(CAR_REQUEST_TOPIC, requestData);

        Long brandId = requestMessage.getBrandId();
        Long modelId = requestMessage.getModelId();
        Year year = requestMessage.getYear() != null ? Year.of(requestMessage.getYear().intValue()) : null;
        List<Car> cars;
        if (modelId == null) {
            if (brandId == null) {
                cars = carRepository.findAllByYear(year);
            } else {
                if (year == null) {
                    cars = carRepository.findAllByModelBrandId(brandId);
                } else {
                    cars = carRepository.findAllByModelBrandIdAndYear(brandId, year);
                }
            }
        } else {
            if (year == null) {
                cars = carRepository.findAllByModelId(modelId);
            } else {
                cars = carRepository.findAllByModelIdAndYear(modelId, year);
            }
        }

        List<Long> carIds = cars.stream().map(Car::getId).collect(Collectors.toList());

        kafkaTemplate.send(CAR_RESPONSE_TOPIC, responseMessageSerializer.serialize(CAR_RESPONSE_TOPIC, new PolicySearchCarResponseMessage(requestMessage.getCorrelationId(), carIds)));
    }

}

