package com.synechron.policycreationservice.dto.kafka;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarDTO {

    private Long id;

    private String image;

    private Year year;

    private Boolean isDeleted;

    private ModelDTO model;

    @JsonProperty("year")
    public void setYear(String year) {
        this.year = Year.parse(year);
    }
}
