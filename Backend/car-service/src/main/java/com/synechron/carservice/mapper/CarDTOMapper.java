package com.synechron.carservice.mapper;

import com.synechron.carservice.dto.CarDTO;
import com.synechron.carservice.model.Brand;
import com.synechron.carservice.model.Car;
import com.synechron.carservice.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarDTOMapper implements  IMapper<Car, CarDTO> {

    @Autowired
    private ModelDTOMapper modelDTOMapper;

    @Override
    public Car toEntity(CarDTO carDto) {
        return Car.builder()
                .id(carDto.getId())
                .year(carDto.getYear())
                .image(carDto.getImage())
                .isDeleted(carDto.getIsDeleted())
                .model(modelDTOMapper.toEntity(carDto.getModel()))
                .build();
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
    public List<CarDTO> listToDTO(List<Car> cars) {
        return cars.stream().map(this::toDTO).collect(Collectors.toList());

    }
}
