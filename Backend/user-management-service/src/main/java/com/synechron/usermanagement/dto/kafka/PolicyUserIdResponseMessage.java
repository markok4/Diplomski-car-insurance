package com.synechron.usermanagement.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class PolicyUserIdResponseMessage {
    private String correlationId;
    private Long userId;
    private int page;
    private int size;
}
