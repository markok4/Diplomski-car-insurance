package com.synechron.usermanagement.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class PolicyUserIdRequestMessage {
    private String correlationId;
    private String email;
    private int page;
    private int size;
}
