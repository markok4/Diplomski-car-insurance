package com.synechron.usermanagement.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SubscriberRoleDTO {
    private Long id;
    private String name;
}
