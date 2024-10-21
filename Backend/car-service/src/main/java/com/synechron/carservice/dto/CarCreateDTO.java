package com.synechron.carservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.Year;

@Data
@Builder
public class CarCreateDTO {
    private Year year;
    private long modelId;
    private long[] carPartIds;
    @JsonIgnore
    private MultipartFile image;
}
