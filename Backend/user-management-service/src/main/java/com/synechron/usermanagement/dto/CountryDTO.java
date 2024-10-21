package com.synechron.usermanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CountryDTO {
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Abbreviation is mandatory")
    private String abbreviation;
    private Boolean isDeleted;
    private LocalDateTime createdAt;
}