package com.synechron.policycreationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PolicyRequestMessage {
    private String correlationId;
    private String subscriberName;
    private String subscriberEmail;
}

