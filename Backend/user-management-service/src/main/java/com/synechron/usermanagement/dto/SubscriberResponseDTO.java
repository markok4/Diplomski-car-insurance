package com.synechron.usermanagement.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubscriberResponseDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean isEnabled;
    private String roleName;
}
