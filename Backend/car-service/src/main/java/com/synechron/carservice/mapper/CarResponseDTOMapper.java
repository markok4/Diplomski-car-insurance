package com.synechron.carservice.mapper;


import com.synechron.carservice.dto.CarDTO;
import com.synechron.carservice.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CarResponseDTOMapper implements IMapper<Car, CarDTO> {

    @Autowired
    private ModelDTOMapper modelDTOMapper;

    @Override
    public Car toEntity(CarDTO car) {
        return null;
    }

    @Override
    public CarDTO toDTO(Car car) {
        return CarDTO.builder()
                .id(car.getId())
                .year(car.getYear())
                .image(car.getImage())
                .isDeleted(car.getIsDeleted())
                .model(modelDTOMapper.toDTO(car.getModel()))
                .build();
    }
    
    @Override
    public List listToDTO(List list) {
        return null;
    }
}