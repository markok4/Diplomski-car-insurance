package com.synechron.usermanagement.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequestDTO {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern.List({
            @Pattern(regexp = ".*[A-Z].*", message = "Password must include a capital letter"),
            @Pattern(regexp = ".*[a-z].*", message = "Password must include a lowercase letter"),
            @Pattern(regexp = ".*\\d.*", message = "Password must include a digit"),
            @Pattern(regexp = ".*[@#\\$%!\\?].*", message = "Password must include a special symbol (@, #, \\$, %, !, ?)"),
            @Pattern(regexp = "\\S+", message = "Password cannot contain whitespace")
    })
    private String password;
}