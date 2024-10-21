package com.synechron.usermanagement.country;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.synechron.usermanagement.config.TestDatabaseConfig;
import com.synechron.usermanagement.dto.CountryDTO;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ContextConfiguration(classes = TestDatabaseConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@EnableConfigurationProperties
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CountryIntegrationTest {
    @Autowired
    protected MockMvc mockMvc;
    protected final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules().configure(
                    SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Test
    public void whenGetAllCountries_thenReturnAllCountries() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/countries"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenDeleteCountry_thenCountryIsDeletedSuccessfully() throws Exception {
        long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/countries/" + id))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Country with ID " + id + " has been successfully deleted"));
    }

    @Test
    public void whenRestoreCountry_thenCountryIsRestoredSuccessfully() throws Exception {
        long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.patch("/countries/restore/" + id))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Country with ID " + id + " has been successfully restored"));
    }

    @Test
    public void whenCreateCountry_thenCountryIsCreatedSuccessfully() throws Exception {
        CountryDTO countryDTO = CountryDTO.builder()
                .name("New Country")
                .abbreviation("NC")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/countries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(countryDTO)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenUpdateCountry_thenCountryIsUpdatedSuccessfully() throws Exception {
        long id = 1L;
        CountryDTO countryDTO = CountryDTO.builder()
                .name("Updated Country")
                .abbreviation("UC")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.patch("/countries/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(countryDTO)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenGetCountryById_thenReturnCountryById() throws Exception {
        long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.get("/countries/" + id))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
}
