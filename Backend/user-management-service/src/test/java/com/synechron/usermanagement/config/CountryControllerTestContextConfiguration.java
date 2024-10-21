package com.synechron.usermanagement.config;

import com.synechron.usermanagement.mapper.CountryMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class CountryControllerTestContextConfiguration {

    @Bean
    public CountryMapper countryMapper() {
        return new CountryMapper();
    }
}
