package com.synechron.paymentservice.dto;


import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CurrencyCreateDto {
    @NotBlank(message = "Currency logo must not be blank")
    private String name;
    @NotBlank(message = "Currency code must not be blank")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency code must be a three-letter uppercase alphabetic code")
    private String code;
    private String logoFilePath;
}
