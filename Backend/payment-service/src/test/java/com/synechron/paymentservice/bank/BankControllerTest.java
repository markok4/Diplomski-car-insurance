package com.synechron.paymentservice.bank;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.synechron.paymentservice.controller.BankController;
import com.synechron.paymentservice.dto.BankDTO;
import com.synechron.paymentservice.entity.Bank;
import com.synechron.paymentservice.mapper.BankDTOMapper;
import com.synechron.paymentservice.service.BankService;
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


@TestConfiguration
class BankControllerTestContextConfiguration {

    @Bean
    public BankDTOMapper bankMapper() {
        return new BankDTOMapper();
    }
}


@WebMvcTest(controllers = BankController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@Import(BankControllerTestContextConfiguration.class)
public class BankControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    BankService bankService;
    @Autowired
    BankDTOMapper bankMapper;


    @Test
    @WithAnonymousUser
    void shouldFindAllBanks() throws Exception{
        List<Bank> banks = getBanks();
        List<BankDTO> bankResponseDto = banks.stream().map(bank -> bankMapper.toDTO(bank)).toList();
        Page<BankDTO> pageOfBanks = new PageImpl<>(bankResponseDto);

        Mockito.when(bankService.getAll(Pageable.ofSize(10))).thenReturn(pageOfBanks);

        mockMvc.perform(get("/banks")).andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("content").exists(),
                        jsonPath("content").isArray(),
                        jsonPath("content", hasSize(banks.size())),
                        jsonPath("content[0].id").value(banks.get(0).getId()),
                        jsonPath("content[1].id").value(banks.get(1).getId()),
                        jsonPath("content[2].id").value(banks.get(2).getId())
                );

    }

    @Test
    @WithAnonymousUser
    void shouldCreateBank() throws Exception {
        Bank bank = new Bank(1L, "Bank1", "logo1", "adresa1", "country1", "city1", 200, LocalDate.of(2020, Month.JANUARY, 18).atStartOfDay(), false, false, null);
        BankDTO bankCreateDTO = bankMapper.toDTO(bank);

        Mockito.when(bankService.create(bankCreateDTO)).thenReturn(bankCreateDTO);

        mockMvc.perform(post("/banks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(bankCreateDTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(asJsonString(bankCreateDTO), true));
    }

    @Test
    @WithAnonymousUser
    void shouldGetBank() throws Exception {
        Long bankId = 1L;
        BankDTO expectedBankDTO = new BankDTO();
        expectedBankDTO.setId(1L);
        expectedBankDTO.setName("Sample Bank");
        expectedBankDTO.setLogo("sampleLogo");
        expectedBankDTO.setAddress("Sample Address");
        expectedBankDTO.setCountry("Sample Country");
        expectedBankDTO.setCity("Sample City");
        expectedBankDTO.setEmployeeNumber(100);
        expectedBankDTO.setCreatedAt(LocalDateTime.of(2021, 1, 1, 12, 0));
        expectedBankDTO.setIsDeleted(false);
        expectedBankDTO.setIsBankrupt(false);

        Mockito.when(bankService.getById(bankId)).thenReturn(expectedBankDTO);

        String expectedJson = asJsonString(expectedBankDTO);

        mockMvc.perform(get("/banks/{id}", bankId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson, true));
    }

    @Test
    @WithAnonymousUser
    void shouldUpdateBank() throws Exception{
        Bank bank = new Bank(1L, "Bank1", "logo1", "adresa1", "country1", "city1", 200, LocalDate.of(2020, Month.JANUARY, 18).atStartOfDay(), false, false, null);
        BankDTO bankUpdateDTO = bankMapper.toDTO(bank);

        Mockito.when(bankService.update(bankUpdateDTO)).thenReturn(bankUpdateDTO);

        mockMvc.perform(put("/banks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(bankUpdateDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(bankUpdateDTO), true));
    }

    @Test
    @WithAnonymousUser
    void shouldDeleteBank() throws Exception{
        Long bankId = 1l;
        doNothing().when(bankService).delete(bankId);

        mockMvc.perform(delete("/banks/{id}", bankId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Successfully deleted bank")));
    }

    @Test
    public void shouldReturn404WhenDeletingBank() throws Exception {
        Long bankId = 1L;
        doThrow(new EntityNotFoundException("Bank not found")).when(bankService).delete(bankId);

        mockMvc.perform(delete("/banks/{id}", bankId))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Error occurred while deleting the bank: Bank not found")));

    }

    @Test
    @WithAnonymousUser
    void shouldRestoreBank() throws Exception{
        Long bankId = 1l;
        doNothing().when(bankService).restore(bankId);

        mockMvc.perform(patch("/banks/{id}/restore", bankId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Successfully restored bank")));
    }

    @Test
    public void shouldReturn404WhenRestoringBank() throws Exception {
        Long bankId = 1L;
        doThrow(new EntityNotFoundException("Bank not found")).when(bankService).restore(bankId);

        mockMvc.perform(patch("/banks/{id}/restore", bankId))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Error occurred while restoring the bank: Bank not found")));

    }

    List<Bank> getBanks() {
        return List.of(
                new Bank(1l, "Bank1", "logo1", "adresa1", "country1", "city1", 200, LocalDate.of(2020, Month.JANUARY, 18).atStartOfDay(), false, false, null),
                new Bank(1l, "Bank2", "logo2", "adresa2", "country2", "city2", 300, LocalDate.of(2020, Month.FEBRUARY, 18).atStartOfDay(), false, false, null),
                new Bank(1l, "Bank3", "logo3", "adresa3", "country3", "city3", 400, LocalDate.of(2020, Month.MARCH, 18).atStartOfDay(), false, false, null)
        );
    }

    private String asJsonString(final Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JavaTimeModule javaTimeModule = new JavaTimeModule();
            javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            objectMapper.registerModule(javaTimeModule);
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
