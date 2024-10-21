package com.synechron.carservice.car;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.synechron.carservice.controller.CarController;
import com.synechron.carservice.mapper.CarDTOMapper;
import com.synechron.carservice.service.CarService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CarController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@Import(BankControllerTestContextConfiguration.class)
public class CarControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    CarService carService;
    @Autowired
    CarDTOMapper carMapper;

    @Test
    @WithAnonymousUser
    void shouldDeleteCar() throws Exception{
        Long carId = 1l;
        doNothing().when(carService).delete(carId);

        mockMvc.perform(delete("/cars/{id}", carId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Successfully deleted car")));
    }

    @Test
    public void shouldReturn404WhenDeletingCar() throws Exception {
        Long carId = 1L;
        doThrow(new EntityNotFoundException("Car not found")).when(carService).delete(carId);

        mockMvc.perform(delete("/cars/{id}", carId))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Error occurred while deleting the car: Car not found")));

    }

    @Test
    @WithAnonymousUser
    void shouldRestoreCar() throws Exception{
        Long carId = 1l;
        doNothing().when(carService).restore(carId);

        mockMvc.perform(patch("/cars/{id}/restore", carId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Successfully restored car")));
    }

    @Test
    public void shouldReturn404WhenRestoringCar() throws Exception {
        Long carId = 1L;
        doThrow(new EntityNotFoundException("Car not found")).when(carService).restore(carId);

        mockMvc.perform(patch("/cars/{id}/restore", carId))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Error occurred while restoring the car: Car not found")));

    }
}
