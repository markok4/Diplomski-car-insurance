package com.synechron.usermanagement.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressResponseDTO {
    private Long id;
    private String street;
    private String streetNumber;
    private Boolean isDeleted;
}
