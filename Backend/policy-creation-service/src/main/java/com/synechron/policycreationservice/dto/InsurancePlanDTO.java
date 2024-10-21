package com.synechron.policycreationservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsurancePlanDTO {
    @NotNull(message = "Insurance Plan ID must be provided")
    private Long id;
    @NotBlank(message = "Insurance plan's name must be provided")
    private String name;
    @JsonProperty("isPremium")
    private Boolean isPremium;
    @JsonProperty("isDeleted")
    private Boolean isDeleted;
    @NotEmpty(message = "At least one insurance item ID must be provided")
    private List<Long> insuranceItemIds; // Only the IDs of the insurance items
}
