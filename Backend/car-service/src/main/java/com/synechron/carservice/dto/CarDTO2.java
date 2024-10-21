package com.synechron.carservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Year;
import java.util.Set;

@Data
@Builder
public class CarDTO2 {
    private Year year;
    private String model;
    private Set<String> carParts;
    private String image;
}