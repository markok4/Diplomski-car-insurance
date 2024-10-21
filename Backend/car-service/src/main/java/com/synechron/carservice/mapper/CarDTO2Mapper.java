package com.synechron.carservice.mapper;

import com.synechron.carservice.dto.CarDTO2;
import com.synechron.carservice.model.Car;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CarDTO2Mapper {

    public CarDTO2 toCarDTO(Car car) {
        Set<String> carParts = new HashSet<String>();
        car.getCarParts().forEach(part -> carParts.add(part.getDescription()));

        return CarDTO2.builder()
                .year(car.getYear())
                .image(car.getImage())
                .model(car.getModel().getName())
                .carParts(carParts)
                .build();

    }

}
