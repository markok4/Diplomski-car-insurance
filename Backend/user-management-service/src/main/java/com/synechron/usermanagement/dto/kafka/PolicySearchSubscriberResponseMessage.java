package com.synechron.usermanagement.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolicySearchSubscriberResponseMessage {
    private String correlationId;
    private List<Long> subscriberIds;
}
