package com.synechron.usermanagement.country;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.synechron.usermanagement.config.CountryControllerTestContextConfiguration;
import com.synechron.usermanagement.controller.CountryController;
import com.synechron.usermanagement.dto.CountryDTO;
import com.synechron.usermanagement.dto.CountryResponseDTO;
import com.synechron.usermanagement.mapper.CountryMapper;
import com.synechron.usermanagement.model.Country;
import com.synechron.usermanagement.service.CountryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = CountryController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@Import(CountryControllerTestContextConfiguration.class)
class CountryControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    CountryService countryService;

    @Autowired
    CountryMapper countryMapper;

    @Test
    @WithAnonymousUser()
    void whenGetAllCountries_thenReturnsPageOfCountryResponseDTO() throws Exception {
        List<Country> countries = getCountries();
        List<CountryResponseDTO> countryResponseDTOS = countries.stream().map(country -> countryMapper.toResponseDTO(country)).toList();
        Page<CountryResponseDTO> pageOfCountries = new PageImpl<>(countryResponseDTOS);

        Mockito.when(countryService.getAll(Pageable.ofSize(10))).thenReturn(pageOfCountries);

        mockMvc.perform(get("/countries")).andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("content").exists(),
                        jsonPath("content").isArray(),
                        jsonPath("content", hasSize(countries.size())),
                        jsonPath("content[0].id").value(countries.get(0).getId()),
                        jsonPath("content[1].id").value(countries.get(1).getId()),
                        jsonPath("content[2].id").value(countries.get(2).getId())
                );
    }

    @Test
    public void whenDeleteExistingCountry_thenStatusOk() throws Exception {
        Long countryId = 1L;
        doNothing().when(countryService).delete(countryId);

        mockMvc.perform(delete("/countries/{id}", countryId))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("successfully deleted")));
    }

    @Test
    public void whenDeleteNonExistentCountry_thenStatusNotFound() throws Exception {
        Long countryId = 1L;
        doThrow(new EntityNotFoundException("Country not found")).when(countryService).delete(countryId);

        mockMvc.perform(delete("/countries/{id}", countryId))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Country not found")));

    }

    @Test
    public void whenRestoreExistingCountry_thenStatusOk() throws Exception {
        Long countryId = 1L;
        doNothing().when(countryService).restore(countryId);

        mockMvc.perform(patch("/countries/restore/{id}", countryId))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("successfully restored")));
    }

    @Test
    public void whenRestoreNonExistentCountry_thenStatusNotFound() throws Exception {
        Long countryId = 1L;
        doThrow(new EntityNotFoundException("Country not found")).when(countryService).restore(countryId);

        mockMvc.perform(patch("/countries/restore/{id}", countryId))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Country not found")));
    }

    @Test
    public void whenCreateCountry_thenStatusCreated() throws Exception {
        CountryResponseDTO createdModelDTO = CountryResponseDTO.builder()
                .name("Italy")
                .abbreviation("IT")
                .createdAt(LocalDate.of(2019, 12, 21).atStartOfDay())
                .build();

        Mockito.when(countryService.create(any(CountryDTO.class))).thenReturn(createdModelDTO);

        mockMvc.perform(post("/countries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(createdModelDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(asJsonString(createdModelDTO)));
    }

    @Test
    public void whenUpdateExistingCountry_thenStatusOk() throws Exception {
        long countryId = 1L;
        CountryResponseDTO updatedCountry = CountryResponseDTO.builder()
                .name("Italy")
                .abbreviation("IT")
                .createdAt(LocalDate.of(2019, 12, 21).atStartOfDay())
                .build();

        Mockito.when(countryService.update(eq(countryId), any(CountryDTO.class))).thenReturn(updatedCountry);

        mockMvc.perform(patch("/countries/{id}", countryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedCountry)))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(updatedCountry)));
    }

    @Test
    public void whenGetExistingCountryById_thenStatusOk() throws Exception {
        Long countryId = 1L;
        CountryResponseDTO countryResponseDTO = CountryResponseDTO.builder()
                .name("Italy")
                .abbreviation("IT")
                .createdAt(LocalDate.of(2019, 12, 21).atStartOfDay())
                .build();

        Mockito.when(countryService.getById(countryId)).thenReturn(Optional.of(countryResponseDTO));

        mockMvc.perform(get("/countries/{id}", countryId))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(countryResponseDTO)));
    }

    @Test
    public void whenGetNonExistentCountryById_thenStatusNotFound() throws Exception {
        Long countryId = 1L;

        Mockito.when(countryService.getById(countryId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/countries/{id}", countryId))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Country not found")));
    }

    List<Country> getCountries() {
        return List.of(
                new Country(1l, "Serbia", "RS", LocalDate.of(2020, Month.JANUARY, 18).atStartOfDay(), null, false),
                new Country(2l, "Germany", "DE", LocalDate.of(2022, Month.FEBRUARY, 28).atStartOfDay(), null, false),
                new Country(3l, "Netherlands", "NL", LocalDate.of(2024, Month.APRIL, 24).atStartOfDay(), null, false)
        );
    }

    static String asJsonString(final Object obj) {

        try {
            return new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                    .writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}