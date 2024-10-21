package com.synechron.policycreationservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ProposalResponseDTO {
    private Long id;
    private Boolean isValid;
    private LocalDateTime creationDate;
    private String proposalStatus;
    private Double amount;
    private String carPlates;
    private Boolean isDeleted;
    private String salesAgentEmail;
    private Long subscriberId;
}
