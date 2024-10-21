package com.synechron.policycreationservice.dto.kafka;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BrandDTO {

    private Long id;

    private String name;

    private String logoImage;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate;

    private Boolean isDeleted;
}
