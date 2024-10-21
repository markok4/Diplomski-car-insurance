package com.synechron.policycreationservice.dto;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.synechron.policycreationservice.model.Proposal;
import com.synechron.policycreationservice.model.ProposalStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
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
public class PolicyDTO {
    @NotNull(message = "Currency ID must be provided")
    private Long id;
    @NotBlank(message = "Date signed must be provided")
    private String dateSigned;
    private String expiringDate;
    private String moneyReceivedDate;
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    private Double amount;
    @JsonProperty("isDeleted")
    private Boolean isDeleted;
    private String carPlates;
    private ProposalDTO proposal;
}
