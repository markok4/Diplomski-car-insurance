package com.synechron.policycreationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class PolicyResponseMessage {
    private String correlationId;
    private List<Long> subscriberIds;
}

