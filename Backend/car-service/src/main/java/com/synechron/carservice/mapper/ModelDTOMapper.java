package com.synechron.carservice.mapper;

import com.synechron.carservice.dto.CarDTO;
import com.synechron.carservice.dto.ModelCreateDTO;
import com.synechron.carservice.dto.ModelDTO;
import com.synechron.carservice.model.Car;
import com.synechron.carservice.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModelDTOMapper {
    @Autowired
    private BrandDTOMapper brandDTOMapper;

    public Model toEntity(ModelDTO modelDTO) {
        return Model.builder()
                .id(modelDTO.getId())
                .name(modelDTO.getName())
                .isDeleted(modelDTO.getIsDeleted())
                .createdAt(modelDTO.getCreatedAt())
                .brand(brandDTOMapper.toEntity(modelDTO.getBrand()))
                .build();
    }

    public ModelDTO toDTO(Model model) {
        return ModelDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .isDeleted(model.getIsDeleted())
                .createdAt(model.getCreatedAt())
                .brand(brandDTOMapper.toDTO(model.getBrand()))
                .build();
    }

    public Model fromCreateDTO(ModelCreateDTO dto) {
        return Model.builder()
                .name(dto.getName())
                .isDeleted(false)
                .build();
    }
}