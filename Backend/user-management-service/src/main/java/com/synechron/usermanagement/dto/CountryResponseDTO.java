package com.synechron.usermanagement.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CountryResponseDTO{
    private Long id;
    private String name;
    private String abbreviation;
    private Boolean isDeleted;
    private LocalDateTime createdAt;
}

