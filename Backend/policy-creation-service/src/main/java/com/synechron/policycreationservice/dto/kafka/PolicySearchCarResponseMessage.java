package com.synechron.policycreationservice.dto.kafka;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PolicySearchCarResponseMessage {
    private String correlationId;
    private List<Long> carIds;
}
