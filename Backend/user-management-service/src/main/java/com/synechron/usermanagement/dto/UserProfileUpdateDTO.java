package com.synechron.usermanagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.synechron.usermanagement.model.Gender;
import com.synechron.usermanagement.model.MaritialStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
@Data
@Builder
public class UserProfileUpdateDTO {
    @NotBlank(message = "First name is mandatory")
    private String firstName;
    @NotBlank(message = "Last name is mandatory")
    private String lastName;
    @NotBlank(message = "JMBG is mandatory")
    @Size(max = 11, message = "JMBG must not exceed 11 characters")
    private String jmbg;
    private LocalDateTime birth;
    private Gender gender;
    private MaritialStatus maritialStatus;
    @JsonIgnore
    private MultipartFile profileImage;
}
