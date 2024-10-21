package com.synechron.paymentservice.currency;

import com.synechron.paymentservice.config.CurrencyControllerTestContextConfiguration;
import com.synechron.paymentservice.controller.CurrencyController;
import com.synechron.paymentservice.dto.CurrencyDto;
import com.synechron.paymentservice.entity.Currency;
import com.synechron.paymentservice.exception.CurrencyNotFoundException;
import com.synechron.paymentservice.mapper.CurrencyMapper;
import com.synechron.paymentservice.service.CurrencyService;
import com.synechron.paymentservice.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CurrencyController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@Import(CurrencyControllerTestContextConfiguration.class)
public class CurrencyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyService currencyService;

    @Mock
    private CurrencyMapper currencyMapper;

    @Test
    void whenGetAllCurrencies_thenReturnsPageOfCurrencyDto() throws Exception {
        List<Currency> currencies = getCurrencies();
        Page<Currency> currencyPage = new PageImpl<>(currencies);

        Mockito.when(currencyService.getAll(anyInt(), anyInt())).thenReturn(currencyPage);

        // Mock the CurrencyMapper to convert each Currency to a CurrencyDto
        for (Currency currency : currencies) {
            CurrencyDto currencyDto = new CurrencyDto(
                    currency.getId(),
                    currency.getName(),
                    currency.getCode(),
                    currency.getLogo(),
                    currency.getIsDeleted(),
                    DateUtil.dateToString(currency.getCreationDate()),
                    DateUtil.dateToString(currency.getLastUpdated())
            );
            // Ensure that currencyMapper is a mock
            when(currencyMapper.toDTO(currency)).thenReturn(currencyDto);

        }

        mockMvc.perform(get("/currencies")
                        .param("page", "0")
                        .param("size", "10"))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.content", hasSize(currencies.size())))
                        .andExpect(jsonPath("$.content[0].id", equalTo(currencies.get(0).getId().intValue())))
                        .andExpect(jsonPath("$.content[1].id", equalTo(currencies.get(1).getId().intValue())))
                        .andExpect(jsonPath("$.content[2].id", equalTo(currencies.get(2).getId().intValue())));
    }

    @Test
    public void whenToggleDeletedStatus_thenStatusOk() throws Exception {
        Long currencyId = 1L;
        doNothing().when(currencyService).toggleDeletedStatus(currencyId);

        mockMvc.perform(patch("/currencies/{id}/toggle-deleted", currencyId))
                .andExpect(status().isNoContent());
    }

    @Test
    public void whenToggleNonExistentCurrency_thenStatusNotFound() throws Exception {
        Long currencyId = 1L;
        doThrow(new CurrencyNotFoundException(currencyId)).when(currencyService).toggleDeletedStatus(currencyId);

        mockMvc.perform(patch("/currencies/{id}/toggle-deleted", currencyId))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Currency not found with id: " + currencyId)));
    }

    @Test
    void whenCreateCurrencyFails_thenStatusBadRequest() throws Exception {
        // Simulate a scenario where currencyService.createCurrency method returns an error response
        when(currencyService.createCurrency(anyString(), anyString(), any())).thenReturn(null);  // For example, returning null to simulate a failure

        // Perform the POST request using MockMVC
        mockMvc.perform(MockMvcRequestBuilders.multipart("/currencies")
                        .param("name", "US Dollar")
                        .param("code", "USD")
                        .contentType(MediaType.MULTIPART_FORM_DATA)) // Explicitly set content type
                .andExpect(status().isBadRequest());  // Expecting 400 Bad Request
    }

    @Test
    void whenUpdateCurrencyFails_thenStatusBadRequest() throws Exception {
        // Simulate a scenario where currencyService.updateCurrency method returns an error response
        when(currencyService.updateCurrency(anyLong(), anyString(), anyString(), isNull())).thenReturn(null);  // For example, returning null to simulate a failure

        // Perform the PATCH request using MockMVC
        mockMvc.perform(MockMvcRequestBuilders.patch("/currencies/{id}", 1L)
                        .param("name", "US Dollar")
                        .param("code", "USD"))
                .andExpect(status().isBadRequest());  // Expecting 400 Bad Request
    }

    @Test
    void whenGetCurrencyLogoExists_thenStatusOk() throws Exception {
        // Prepare a test image file

        byte[] testImageBytes = Files.readAllBytes(Paths.get("src/main/resources/uploads/currency-logos/currency_1714054910785.png"));;  // Replace "path_to_test_image.png" with the actual path to your test PNG file

        // Write the test image to a temporary file
        Path tempImageFile = Files.createTempFile("test-image", ".png");
        Files.write(tempImageFile, testImageBytes);

        // Perform the GET request using MockMVC
        mockMvc.perform(get("/currencies/logos/currency_1714054910785.png"))
                .andExpect(status().isOk());  // Expecting 200 OK

        // Delete the temporary image file after the test
        Files.delete(tempImageFile);
    }

    public List<Currency> getCurrencies() {
        return List.of(
                new Currency(1L, "US Dollar", "USD", "usd-logo.png", new Date(), new Date(), false, true, new ArrayList<>()),
                new Currency(2L, "Euro", "EUR", "eur-logo.png", new Date(), new Date(), false, true, new ArrayList<>()),
                new Currency(3L, "British Pound", "GBP", "gbp-logo.png", new Date(), new Date(), false, true, new ArrayList<>())
        );
    }

}
