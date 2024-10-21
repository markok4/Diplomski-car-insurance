package com.synechron.carservice.car;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.synechron.carservice.config.TestDatabaseConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ContextConfiguration(classes = TestDatabaseConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@EnableConfigurationProperties
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarIntegrationTest {
    @Autowired
    protected MockMvc mockMvc;
    protected final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules().configure(
                    SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Test
    @WithMockUser
    public void whenDeleteCar_thenCarIsDeletedSuccessfully() throws Exception {
        long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/cars/" + id).with(csrf()))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Successfully deleted car"));
    }

    @Test
    @WithMockUser
    public void whenRestoreCar_thenCarIsRestoredSuccessfully() throws Exception {
        long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.patch("/cars/" + id + "/restore").with(csrf()))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Successfully restored car"));
    }
}
