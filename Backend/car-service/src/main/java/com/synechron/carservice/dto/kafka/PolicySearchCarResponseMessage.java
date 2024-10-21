package com.synechron.carservice.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PolicySearchCarResponseMessage {
    private String correlationId;
    private List<Long> carIds;
}
