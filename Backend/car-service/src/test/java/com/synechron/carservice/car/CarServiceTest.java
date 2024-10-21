package com.synechron.carservice.car;

import com.synechron.carservice.mapper.CarDTOMapper;
import com.synechron.carservice.model.Car;
import com.synechron.carservice.repository.CarRepository;
import com.synechron.carservice.service.CarService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CarServiceTest {
    @Mock
    CarDTOMapper bankMapper;
    @Mock
    CarRepository carRepository;
    @InjectMocks
    CarService carService;

    @Test
    public void givenCarId_whenDeleteCar_thenSetIsDeletedToTrue(){
        Long carId = 1l;
        Car car = new Car();

        given(carRepository.findById(carId)).willReturn(Optional.of(car));

        carService.delete(carId);

        assertTrue(car.getIsDeleted());
        verify(carRepository).save(car);
    }

    @Test
    public void givenNonExistingCarId_whenDeleteCar_thenThrowEntityNotFoundException(){
        Long carId = 1l;

        when(carRepository.findById(carId)).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(
                EntityNotFoundException.class,
                () -> carService.delete(carId),
                "Expected delete to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Entity with id " + carId + " does not exist"));
    }

    @Test
    public void givenCarId_whenRestoreCar_thenSetIsDeletedToFalse(){
        Long carId = 1l;
        Car car = new Car();

        given(carRepository.findById(carId)).willReturn(Optional.of(car));

        carService.restore(carId);

        assertFalse(car.getIsDeleted());
        verify(carRepository).save(car);
    }

    @Test
    public void givenNonExistingCarId_whenRestoreCar_thenThrowEntityNotFoundException(){
        Long carId = 1l;

        when(carRepository.findById(carId)).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(
                EntityNotFoundException.class,
                () -> carService.restore(carId),
                "Expected delete to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Entity with id " + carId + " does not exist"));
    }
}
