package com.synechron.paymentservice.bank;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.synechron.paymentservice.config.TestDatabaseConfig;
import com.synechron.paymentservice.dto.BankDTO;
import jakarta.ws.rs.core.MediaType;
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
public class BankIntegrationTest {
    @Autowired
    protected MockMvc mockMvc;
    protected final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules().configure(
                    SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Test
    @WithMockUser
    public void whenGetAllBanks_thenReturnAllBanks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/banks").with(csrf()))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser
    public void whenGetBankById_thenReturnBankById() throws Exception {
        long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.get("/banks/" + id).with(csrf()))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser
    public void whenDeleteBank_thenBankIsDeletedSuccessfully() throws Exception {
        long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/banks/" + id).with(csrf()))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Successfully deleted bank"));
    }

    @Test
    @WithMockUser
    public void whenRestoreBank_thenBankIsRestoredSuccessfully() throws Exception {
        long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.patch("/banks/" + id + "/restore").with(csrf()))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Successfully restored bank"));
    }

    @Test
    @WithMockUser
    public void whenCreateBank_thenBankIsCreatedSuccessfully() throws Exception {
        BankDTO bankDTO = new BankDTO();
        bankDTO.setName("Sample Bank");
        bankDTO.setLogo("sampleLogo");
        bankDTO.setAddress("Sample Address");
        bankDTO.setCountry("Sample Country");
        bankDTO.setCity("Sample City");
        bankDTO.setEmployeeNumber(100);


        mockMvc.perform(MockMvcRequestBuilders.post("/banks").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bankDTO)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser
    public void whenUpdateBank_thenBankIsUpdatedSuccessfully() throws Exception {
        BankDTO bankDTO = new BankDTO();
        bankDTO.setId(1l);
        bankDTO.setName("Updated Bank");
        bankDTO.setLogo("Updated Logo");
        bankDTO.setAddress("Updated Address");
        bankDTO.setCountry("Updated Country");
        bankDTO.setCity("Updated City");
        bankDTO.setEmployeeNumber(200);

        mockMvc.perform(MockMvcRequestBuilders.put("/banks").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bankDTO)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
}
