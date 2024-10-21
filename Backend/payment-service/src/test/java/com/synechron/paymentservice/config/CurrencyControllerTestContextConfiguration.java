package com.synechron.paymentservice.config;

import com.synechron.paymentservice.mapper.CurrencyMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class CurrencyControllerTestContextConfiguration {
    @Bean
    public CurrencyMapper currencyMapper() {
        return new CurrencyMapper();
    }
}
