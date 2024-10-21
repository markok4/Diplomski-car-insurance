package com.synechron.carservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import lombok.Data;

import java.time.Year;

@Data
@Builder
public class CarDTO {
    @NotNull
    private Long id;
    @PastOrPresent
    private Year year;
    @NotBlank(message = "Image field can't be blank")
    private String image;
    private Boolean isDeleted;
    @NotNull
    private ModelDTO model;
}
