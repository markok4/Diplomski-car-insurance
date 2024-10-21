package com.synechron.carservice.dto.kafka;

import lombok.Data;

@Data
public class PolicySearchCarRequestMessage {
    private String correlationId;
    private Long brandId;
    private Long modelId;
    private Long year;
}
