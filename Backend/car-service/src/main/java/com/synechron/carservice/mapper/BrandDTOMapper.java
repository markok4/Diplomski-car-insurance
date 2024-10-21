package com.synechron.carservice.mapper;

import com.synechron.carservice.dto.BrandDTO;
import com.synechron.carservice.model.Brand;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BrandDTOMapper implements  IMapper<Brand, BrandDTO> {
    @Override
    public BrandDTO toDTO(Brand brand) {
        BrandDTO dto = new BrandDTO();
        dto.setId(brand.getId());
        dto.setName(brand.getName());
        dto.setCreationDate(brand.getCreationDate());
        dto.setLogoImage(brand.getLogoImage());
        dto.setIsDeleted(brand.getIsDeleted());

        return dto;
    }

    @Override
    public Brand toEntity(BrandDTO brandDto) {
        Brand brand = new Brand();

        brand.setId(brandDto.getId());
        brand.setName(brandDto.getName());
        brand.setCreationDate(brandDto.getCreationDate());
        brand.setLogoImage((brandDto.getLogoImage()));
        brand.setIsDeleted(brandDto.getIsDeleted());

        return brand;

    }

    @Override
    public List<BrandDTO> listToDTO(List<Brand> brands) {
        List<BrandDTO> dtos = new ArrayList<>();
        for (Brand brand : brands) {
            dtos.add(toDTO(brand));
        }
        return dtos;

    }
}
