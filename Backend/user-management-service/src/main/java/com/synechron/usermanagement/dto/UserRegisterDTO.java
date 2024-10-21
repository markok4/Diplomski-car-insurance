package com.synechron.usermanagement.dto;

import com.synechron.usermanagement.model.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
public class UserRegisterDTO {
    @NotBlank(message = "First name is required.")
    private String firstName;
    @NotBlank(message = "Last name is required.")
    private String lastName;
    @NotBlank(message = "Password is required.")
    private String password;
    @NotBlank(message = "JMBG is required.")
    private String jmbg;
    @NotBlank(message = "Birth is required.")
    private LocalDateTime birth;
    @NotBlank(message = "Gender is required.")
    private Gender gender;
    @NotBlank(message = "Marital status is required.")
    private MaritialStatus maritialStatus;
    @NotBlank(message = "User role is required.")
    private UserRole role;
    @NotBlank(message = "Contact is required.")
    private ContactCreateDTO contact;
    @NotBlank(message = "Email is required.")
    @Email(message = "Email is not well-formed.")
    private String email;
    @NotBlank(message = "Address is required.")
    private Long address;
    private Boolean isEnabled;
    private Boolean isActive;
}
