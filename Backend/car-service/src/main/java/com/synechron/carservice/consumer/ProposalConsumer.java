package com.synechron.carservice.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.synechron.carservice.dto.CarDTO;
import com.synechron.carservice.producer.CarProducer;
import com.synechron.carservice.service.CarService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProposalConsumer {

    @Autowired
    private CarProducer carProducer;

    @Autowired
    private CarService carService;

    @KafkaListener(topics = {"car-details-request"})
    public void listenForProposal(ConsumerRecord<String, String> consumerRecord) {
        log.info("Consumer record: {}", consumerRecord);
        try {
            CarDTO car = carService.getById(Long.valueOf(consumerRecord.value()));
            carProducer.sendCar(car, consumerRecord.key());
        } catch (EntityNotFoundException | JsonProcessingException ex) {
            log.error(ex.getMessage());
        }
    }
}
