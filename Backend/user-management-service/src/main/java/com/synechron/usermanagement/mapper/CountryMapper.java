package com.synechron.usermanagement.mapper;

import com.synechron.usermanagement.dto.CountryDTO;
import com.synechron.usermanagement.dto.CountryResponseDTO;
import com.synechron.usermanagement.model.Country;
import org.springframework.stereotype.Component;

@Component
public class CountryMapper {
    public CountryResponseDTO toResponseDTO(Country country) {
        return CountryResponseDTO.builder()
                .id(country.getId())
                .name(country.getName())
                .abbreviation(country.getAbbreviation())
                .createdAt(country.getCreatedAt())
                .isDeleted(country.getIsDeleted())
                .build();
    }

    public Country toEntity(CountryDTO dto) {
        return Country.builder()
                .name(dto.getName())
                .abbreviation(dto.getAbbreviation())
                .isDeleted(false)
                .build();
    }
}
