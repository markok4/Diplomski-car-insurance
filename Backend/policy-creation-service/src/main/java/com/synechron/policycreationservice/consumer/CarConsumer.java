package com.synechron.policycreationservice.consumer;

import com.synechron.policycreationservice.dto.kafka.CarDTO;
import com.synechron.policycreationservice.mapper.kafka.CarMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
@Slf4j
@Getter
public class CarConsumer {

    @Autowired
    private CarMapper carDTOMapper;

    private final ConcurrentMap<String, CompletableFuture<CarDTO>> futures = new ConcurrentHashMap<>();

    @KafkaListener(topics = {"car-details-response"})
    public void listenForCar(ConsumerRecord<String, String> consumerRecord) {
        log.info("Consumer record: {}", consumerRecord);

        CarDTO car = carDTOMapper.toDTO(consumerRecord.key());
        String correlationId = consumerRecord.value();

        CompletableFuture<CarDTO> future = futures.remove(correlationId);
        if (future != null) {
            future.complete(car);
        } else {
            log.warn("No future found for correlationId: {}", correlationId);
        }
    }

    public void addCarFuture(String correlationId, CompletableFuture<CarDTO> future) {
        futures.put(correlationId, future);
    }
}
