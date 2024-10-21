package com.synechron.policycreationservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SetProposalSubscriberDTO {
    private long subscriberId;
    private long proposalId;
}
