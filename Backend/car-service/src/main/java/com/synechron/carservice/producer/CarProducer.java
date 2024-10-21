package com.synechron.carservice.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.synechron.carservice.dto.CarDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class CarProducer {

    @Value("${spring.kafka.topic}")
    public String topic;

    private ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public CarProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public CompletableFuture<SendResult<String, String>> sendCar(CarDTO car, String correlationId) throws JsonProcessingException {
        String key = objectMapper.writeValueAsString(car);
        String value = correlationId;

        var producerRecord = buildProducerRecord(key, value);

        var completableFuture = kafkaTemplate.send(producerRecord);

        return completableFuture.whenComplete((sendResult, throwable) -> {
            if (throwable != null) {
                handleFailure(key, value, throwable);
            }
            else {
                handleSuccess(key, value, sendResult);
            }
        });
    }

    private ProducerRecord<String, String> buildProducerRecord(String key, String value) {
        return new ProducerRecord<>(topic, key, value);
    }

    private void handleSuccess(String key, String value, SendResult<String, String> throwable) {
        log.info("Car successfully sent.");
    }

    private void handleFailure(String key, String value, Throwable throwable) {
        log.error("Error. Car not sent.");
    }
}
