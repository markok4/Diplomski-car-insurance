package com.synechron.policycreationservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.synechron.policycreationservice.dto.kafka.CarDTO;
import com.synechron.policycreationservice.model.ProposalStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProposalDTO {
    @NotNull(message = "Currency ID must be provided")
    private Long id;
    private Boolean isValid;
    private String creationDate;
    private ProposalStatus proposalStatus;
    @NotNull(message = "Amount must be provided")
    private Double amount;
    @NotBlank(message = "Car Plates must be provided")
    private String carPlates;
    @JsonProperty("isDeleted")
    private Boolean isDeleted;
    private Long subscriberId;
    private InsurancePlanDTO insurancePlan;
    private CarDTO car;
}
