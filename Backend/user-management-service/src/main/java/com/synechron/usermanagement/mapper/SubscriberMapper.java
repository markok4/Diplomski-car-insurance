package com.synechron.usermanagement.mapper;

import com.synechron.usermanagement.dto.SubscriberResponseDTO;
import com.synechron.usermanagement.dto.SubscriberRoleDTO;
import com.synechron.usermanagement.model.Subscriber;
import com.synechron.usermanagement.model.SubscriberRole;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SubscriberMapper {
    public SubscriberResponseDTO toResponseDTO(Subscriber subscriber) {
        return SubscriberResponseDTO.builder()
                .id(subscriber.getId())
                .firstName(subscriber.getFirstName())
                .lastName(subscriber.getLastName())
                .email(subscriber.getEmail())
                .isEnabled(subscriber.getIsEnabled())
                .roleName(subscriber.getSubscriberRole().getName())
                .build();
    }

    public SubscriberRoleDTO roleToDTO(SubscriberRole role) {
        return SubscriberRoleDTO.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }

    public List<SubscriberResponseDTO> toResponseDTOList(List<Subscriber> subscribers) {
        return subscribers.stream()
                .map(this::toResponseDTO) // Use the existing toResponseDTO method
                .collect(Collectors.toList());
    }
}
