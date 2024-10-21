package com.synechron.carservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ModelDTO {
    private Long id;
    private String name;
    private Boolean isDeleted;
    private LocalDateTime createdAt;
    private BrandDTO brand;
}


