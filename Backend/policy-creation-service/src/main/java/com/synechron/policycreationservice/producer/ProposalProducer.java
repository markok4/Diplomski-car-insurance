package com.synechron.policycreationservice.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class ProposalProducer {

    @Value("${spring.kafka.topic}")
    public String topic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public ProposalProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public CompletableFuture<SendResult<String, String>> sendIdCar(Long idCar, String correlationId) {
        String key = correlationId;
        String value = String.valueOf(idCar);

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
        log.info("Proposal successfully sent.");
    }

    private void handleFailure(String key, String value, Throwable throwable) {
        log.error("Error. Proposal not sent.");
    }
}
