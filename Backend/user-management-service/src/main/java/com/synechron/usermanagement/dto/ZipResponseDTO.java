package com.synechron.usermanagement.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ZipResponseDTO {
    private Long id;
    private Integer zipNumber;
    private Boolean isDeleted;
}
