package com.synechron.policycreationservice.dto.kafka;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PolicySearchSubscriberResponseMessage {
    private String correlationId;
    private List<Long> subscriberIds;
}
