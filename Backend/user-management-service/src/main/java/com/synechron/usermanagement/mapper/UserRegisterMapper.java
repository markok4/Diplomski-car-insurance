package com.synechron.usermanagement.mapper;

import com.synechron.usermanagement.dto.SubscriberRegisterDTO;
import com.synechron.usermanagement.dto.UserRegisterDTO;
import com.synechron.usermanagement.model.Subscriber;
import com.synechron.usermanagement.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserRegisterMapper {
    public UserRegisterDTO toDTO(User user) {
        return UserRegisterDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .password(user.getPassword())
                .jmbg(user.getJmbg())
                .birth(user.getBirth())
                .gender(user.getGender())
                .maritialStatus(user.getMaritialStatus())
                .role(user.getUserRole())
//                .contact(user.getContact())
                .email(user.getEmail())
//                .address(user.getAddress())
                .isEnabled(user.getIsEnabled())
                .isActive(user.getIsActive())
                .build();
    }

    public User toEntity(UserRegisterDTO dto) {
        return User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .password(dto.getPassword())
                .jmbg(dto.getJmbg())
                .birth(dto.getBirth())
                .gender(dto.getGender())
                .maritialStatus(dto.getMaritialStatus())
                .userRole(dto.getRole())
//                .contact(dto.getContact())
                .email(dto.getEmail())
//                .address(dto.getAddress())
                .isEnabled(dto.getIsEnabled())
                .isActive(dto.getIsActive())
                .build();
    }

    public SubscriberRegisterDTO toDTO(Subscriber user) {
        return SubscriberRegisterDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .password(user.getPassword())
                .jmbg(user.getJmbg())
                .birth(user.getBirth())
                .gender(user.getGender())
                .maritialStatus(user.getMaritialStatus())
                .role(user.getUserRole())
//                .contact(user.getContact())
                .email(user.getEmail())
//                .address(user.getAddress())
                .subscriberRoleId(user.getSubscriberRole().getId())
                .build();
    }

    public Subscriber toEntity(SubscriberRegisterDTO dto) {
        return Subscriber.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .password(dto.getPassword())
                .jmbg(dto.getJmbg())
                .birth(dto.getBirth())
                .gender(dto.getGender())
                .maritialStatus(dto.getMaritialStatus())
                .userRole(dto.getRole())
//                .contact(dto.getContact())
                .email(dto.getEmail())
//                .address(dto.getAddress())
                .build();
    }
}
