package com.synechron.paymentservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CurrencyDto {
    @NotNull(message = "Currency ID must be provided")
    private Long id;
    @NotBlank(message = "Currency logo must not be blank")
    private String name;
    @NotBlank(message = "Currency code must not be blank")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency code must be a three-letter uppercase alphabetic code")
    @JsonProperty("code")
    private String code;
    @NotBlank(message = "Currency logo must not be blank")
    private String logo;
    @JsonProperty("isDeleted")
    private boolean isDeleted;
    private String creationDate;
    private String lastUpdated;
}
