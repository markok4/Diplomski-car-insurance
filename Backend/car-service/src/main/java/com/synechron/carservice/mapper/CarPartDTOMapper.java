package com.synechron.carservice.mapper;

import com.synechron.carservice.dto.CarPartDTO;
import com.synechron.carservice.model.CarPart;
import org.springframework.stereotype.Component;

@Component
public class CarPartDTOMapper {
    public CarPartDTO toDTO(CarPart carPart) {
        return CarPartDTO.builder()
                .id(carPart.getId())
                .description(carPart.getDescription())
                .isDeleted(carPart.getIsDeleted())
                .build();
    }
}
