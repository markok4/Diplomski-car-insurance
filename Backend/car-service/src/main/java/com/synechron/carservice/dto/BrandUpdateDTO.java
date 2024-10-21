package com.synechron.carservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BrandUpdateDTO {

    @NotNull
    private Long id;
    @NotBlank(message = "Name field can't be blank")
    @NotEmpty
    @Size(max=44,min=2,message="Length of name is not correct")
    private String name;
    @NotBlank(message = "Logo image field can't be blank")
    private String logoImage;
    private LocalDate creationDate;
    private boolean isDeleted = false;
}
