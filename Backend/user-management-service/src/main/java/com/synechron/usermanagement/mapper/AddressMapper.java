package com.synechron.usermanagement.mapper;

import com.synechron.usermanagement.dto.AddressResponseDTO;
import com.synechron.usermanagement.model.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    public AddressResponseDTO toResponseDTO(Address address){
        return AddressResponseDTO.builder()
                .id(address.getId())
                .street(address.getStreet())
                .streetNumber(address.getStreetNumber())
                .isDeleted(address.getIsDeleted())
                .build();
    }

    public Address toEntity(AddressResponseDTO dto){
        return Address.builder()
                .id(dto.getId())
                .street(dto.getStreet())
                .streetNumber(dto.getStreetNumber())
                .isDeleted(dto.getIsDeleted())
                .build();
    }
}
