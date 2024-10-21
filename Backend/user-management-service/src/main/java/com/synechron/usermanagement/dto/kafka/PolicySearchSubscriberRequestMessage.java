package com.synechron.usermanagement.dto.kafka;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PolicySearchSubscriberRequestMessage {
    private String correlationId;
    private String nameOrEmail;
}
