package com.synechron.usermanagement.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProposalSubscriberValidMessage {
    private String correlationId;
    private Long subscriberId;
    private Long proposalId;
}
