package com.synechron.usermanagement.mapper;

import com.synechron.usermanagement.dto.CityResponseDTO;
import com.synechron.usermanagement.model.City;
import org.springframework.stereotype.Component;

@Component
public class CityMapper {
    public CityResponseDTO toResponseDTO(City city){
        return CityResponseDTO.builder()
                .id(city.getId())
                .name(city.getName())
                .isDeleted(city.getIsDeleted())
                .build();
    }
}
