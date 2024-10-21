package com.synechron.policycreationservice.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolicySearchSubscriberRequestMessage {
    private String correlationId;
    private String nameOrEmail;
}
