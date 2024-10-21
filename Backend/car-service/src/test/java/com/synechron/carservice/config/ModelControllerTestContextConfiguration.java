package com.synechron.carservice.config;

import com.synechron.carservice.mapper.BrandDTOMapper;
import com.synechron.carservice.mapper.ModelDTOMapper;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ModelControllerTestContextConfiguration {
    @Bean
    public ModelDTOMapper modelDTOMapper() {
        return new ModelDTOMapper();
    }
    @Bean
    public BrandDTOMapper brandDTOMapper() {
        return new BrandDTOMapper();
    }
}
