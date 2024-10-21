package com.synechron.usermanagement.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactCreateDTO {
    private String homePhone;
    private String mobilePhone;
    private String email;
    private Boolean isDeleted;
}
