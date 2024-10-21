package com.synechron.carservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Year;

@Data
public class CarResponseDTO {
    @NotNull
    private Long id;
    @NotBlank(message = "Image is mandatory")
    private String image;
    @NotBlank(message = "Year is mandatory")
    private Year year;
    private Boolean isDeleted;
}
