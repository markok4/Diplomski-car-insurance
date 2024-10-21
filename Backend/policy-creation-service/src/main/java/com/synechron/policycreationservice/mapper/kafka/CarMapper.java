package com.synechron.policycreationservice.mapper.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.synechron.policycreationservice.dto.kafka.CarDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CarMapper {

    @Autowired
    private ObjectMapper objectMapper;

    public CarDTO toDTO(String json) {
        try {
            return objectMapper.readValue(json, CarDTO.class);
        } catch (JsonProcessingException ex) {
            log.error("Error mapping JSON to DTO: {}", ex.getMessage());
        }
        return null;
    }
}
