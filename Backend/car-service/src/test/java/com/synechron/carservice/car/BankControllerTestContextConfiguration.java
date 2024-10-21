package com.synechron.carservice.car;

import com.synechron.carservice.mapper.CarDTOMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
class BankControllerTestContextConfiguration {

    @Bean
    public CarDTOMapper bankMapper() {
        return new CarDTOMapper();
    }
}
