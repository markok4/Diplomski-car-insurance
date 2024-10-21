package com.synechron.carservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarPartDTO {
    private long id;
    private String description;
    private Boolean isDeleted;
}
