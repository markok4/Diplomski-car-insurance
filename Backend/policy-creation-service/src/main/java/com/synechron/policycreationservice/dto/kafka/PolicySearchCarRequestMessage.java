package com.synechron.policycreationservice.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolicySearchCarRequestMessage {
    private String correlationId;
    private Long brandId;
    private Long modelId;
    private Long year;
}
