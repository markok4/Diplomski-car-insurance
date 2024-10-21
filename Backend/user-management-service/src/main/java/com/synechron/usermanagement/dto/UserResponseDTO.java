package com.synechron.usermanagement.dto;

import com.synechron.usermanagement.model.Gender;
import com.synechron.usermanagement.model.MaritialStatus;
import com.synechron.usermanagement.model.UserRole;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String jmbg;
    private LocalDateTime birth;
    private Gender gender;
    private MaritialStatus maritialStatus;
    private String email;
    private Boolean isEnabled;
//    private Address address;
    private UserRole userRole;
    private String profileImage;
}
