package com.synechron.usermanagement.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CityResponseDTO {
    private Long id;
    private String name;
    private Boolean isDeleted;
}
