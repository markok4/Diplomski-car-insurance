package com.synechron.paymentservice.currency;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.synechron.paymentservice.config.TestDatabaseConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration(classes = TestDatabaseConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CurrencyIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .findAndRegisterModules()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Test
    @WithMockUser
    public void whenGetAllCurrencies_thenReturnAllCurrencies() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/currencies").with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser
    public void whenGetCurrencyById_thenReturnCurrencyById() throws Exception {
        Long id = 1L; // Make sure this ID corresponds to a currency in your test database
        mockMvc.perform(MockMvcRequestBuilders.get("/currencies/" + id).with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser
    public void whenCreateCurrencyWithoutLogo_thenBadRequest() throws Exception {
        String name = "Euro";
        String code = "EUR";

        mockMvc.perform(MockMvcRequestBuilders.multipart("/currencies")
                        .param("name", name)
                        .param("code", code)
                        .with(csrf())
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    public void whenToggleDeletedStatus_thenStatusIsToggled() throws Exception {
        Long id = 1L; // Make sure this ID corresponds to a currency in your test database
        mockMvc.perform(MockMvcRequestBuilders.patch("/currencies/" + id + "/toggle-deleted").with(csrf()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


}
