package com.synechron.carservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ModelUpdateDTO {
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotNull(message = "Brand is mandatory")
    private Long brandId;
}