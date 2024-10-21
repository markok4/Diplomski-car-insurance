package com.synechron.policycreationservice.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class PolicyUserIdRequestMessage {
    private String correlationId;
    private String email;
    private int page;
    private int size;
}
