package com.synechron.usermanagement.mapper;

import com.synechron.usermanagement.dto.CountryDTO;
import com.synechron.usermanagement.dto.CountryResponseDTO;
import com.synechron.usermanagement.dto.UserResponseDTO;
import com.synechron.usermanagement.model.Country;
import com.synechron.usermanagement.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public static UserResponseDTO toResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .jmbg(user.getJmbg())
                .birth(user.getBirth())
                .gender(user.getGender())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .maritialStatus(user.getMaritialStatus())
                .isEnabled(user.getIsEnabled())
                .email(user.getEmail())
                .userRole(user.getUserRole())
                .profileImage(user.getProfileImage())
                .build();
    }
}
