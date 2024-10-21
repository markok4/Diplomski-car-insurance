package com.synechron.paymentservice.currency;

import com.synechron.paymentservice.dto.CurrencyDto;
import com.synechron.paymentservice.entity.Currency;
import com.synechron.paymentservice.exception.CurrencyNotFoundException;
import com.synechron.paymentservice.mapper.CurrencyMapper;
import com.synechron.paymentservice.repository.CurrencyRepository;
import com.synechron.paymentservice.service.CurrencyService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CurrencyServiceTest {

    @Mock
    CurrencyMapper currencyMapper;

    @Mock
    CurrencyRepository currencyRepository;

    @InjectMocks
    CurrencyService currencyService;

    @Mock
    private MultipartFile logo;

    @Test
    public void whenGetAll_thenReturnsPageOfCurrency() {
        // Arrange
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        Currency currency = new Currency();
        Page<Currency> currencyPage = new PageImpl<>(Collections.singletonList(currency));

        when(currencyRepository.findAll(any(Pageable.class))).thenReturn(currencyPage);

        // Act
        Page<Currency> result = currencyService.getAll(page, size);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(currency, result.getContent().get(0));
    }

    @Test
    public void whenGetById_thenReturnCurrency() {
        // Arrange
        Long currencyId = 1L;
        Currency currency = new Currency();
        currency.setId(currencyId);

        when(currencyRepository.findById(currencyId)).thenReturn(Optional.of(currency));

        // Act
        Currency foundCurrency = currencyService.getById(currencyId);

        // Assert
        assertNotNull(foundCurrency);
        assertEquals(currencyId, foundCurrency.getId());
    }

    @Test
    public void whenToggleDeletedStatus_thenStatusIsToggled() {
        // Arrange
        Long currencyId = 1L;
        Currency currency = new Currency();
        currency.setIsDeleted(false);
        when(currencyRepository.findById(currencyId)).thenReturn(Optional.of(currency));

        // Act
        currencyService.toggleDeletedStatus(currencyId);

        // Assert
        assertTrue(currency.getIsDeleted());
        verify(currencyRepository).save(currency);
    }

    @Test
    public void whenToggleDeletedStatusOnNonExistentCurrency_thenThrowException() {
        // Arrange
        Long currencyId = 1L;
        when(currencyRepository.findById(currencyId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CurrencyNotFoundException.class, () -> {
            currencyService.toggleDeletedStatus(currencyId);
        });
    }


    @Test
    public void whenGetById_thenThrowCurrencyNotFoundException() {
        // Arrange
        Long currencyId = 1L;
        when(currencyRepository.findById(currencyId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CurrencyNotFoundException.class, () -> {
            currencyService.getById(currencyId);
        });
    }

    @Test
    public void whenCreateCurrencyWithLogo_thenCurrencyIsCreatedWithLogo() throws IOException {
        // Arrange
        String name = "Dollar";
        String code = "USD";
        String originalFileName = "dollar_logo.png";
        String fileExtension = "png";
        byte[] logoContent = new byte[20];
        when(logo.isEmpty()).thenReturn(false);
        when(logo.getOriginalFilename()).thenReturn(originalFileName);
        when(logo.getBytes()).thenReturn(logoContent);
        Currency currency = new Currency();
        currency.setName(name);
        currency.setCode(code);
        CurrencyDto currencyDto = new CurrencyDto();
        when(currencyRepository.save(any(Currency.class))).thenReturn(currency);
        when(currencyMapper.toDTO(any(Currency.class))).thenReturn(currencyDto);

        // Act
        CurrencyDto createdCurrencyDto = currencyService.createCurrency(name, code, logo);

        // Assert
        assertNotNull(createdCurrencyDto);
        verify(currencyRepository).save(any(Currency.class));
        verify(currencyMapper).toDTO(any(Currency.class));
        verify(logo).getBytes();
    }

    @Test
    public void whenUpdateCurrency_thenCurrencyIsUpdated() throws IOException {
        // Arrange
        Long currencyId = 1L;
        String name = "Euro";
        String code = "EUR";
        Currency currency = new Currency();
        currency.setId(currencyId);
        currency.setName("Old Name");
        currency.setCode("Old Code");
        when(currencyRepository.findById(currencyId)).thenReturn(Optional.of(currency));
        CurrencyDto currencyDto = new CurrencyDto();
        when(currencyRepository.save(any(Currency.class))).thenReturn(currency);
        when(currencyMapper.toDTO(any(Currency.class))).thenReturn(currencyDto);
        when(logo.isEmpty()).thenReturn(true);

        // Act
        CurrencyDto updatedCurrencyDto = currencyService.updateCurrency(currencyId, name, code, logo);

        // Assert
        assertNotNull(updatedCurrencyDto);
        verify(currencyRepository).save(currency);
        verify(currencyMapper).toDTO(currency);
        assertEquals(name, currency.getName());
        assertEquals(code, currency.getCode());
        verify(logo, never()).getBytes();
    }

    @Test
    public void whenUpdateCurrencyWithLogo_thenCurrencyIsUpdatedWithLogo() throws IOException {
        // Arrange
        Long currencyId = 1L;
        String name = "Dollar";
        String code = "USD";
        String originalFileName = "dollar_logo.png";
        byte[] logoContent = new byte[20];
        when(logo.isEmpty()).thenReturn(false);
        when(logo.getOriginalFilename()).thenReturn(originalFileName);
        when(logo.getBytes()).thenReturn(logoContent);
        Currency currency = new Currency();
        currency.setId(currencyId);
        currency.setName("Old Name");
        currency.setCode("Old Code");
        when(currencyRepository.findById(currencyId)).thenReturn(Optional.of(currency));
        CurrencyDto currencyDto = new CurrencyDto();
        when(currencyRepository.save(any(Currency.class))).thenReturn(currency);
        when(currencyMapper.toDTO(any(Currency.class))).thenReturn(currencyDto);

        // Act
        CurrencyDto updatedCurrencyDto = currencyService.updateCurrency(currencyId, name, code, logo);

        // Assert
        assertNotNull(updatedCurrencyDto);
        verify(currencyRepository).save(currency);
        verify(currencyMapper).toDTO(currency);
        assertEquals(name, currency.getName());
        assertEquals(code, currency.getCode());
        verify(logo).getBytes();
    }

    @Test
    public void whenUpdateNonexistentCurrency_thenThrowCurrencyNotFoundException() {
        // Arrange
        Long currencyId = 1L;
        String name = "Invalid";
        String code = "INV";
        when(currencyRepository.findById(currencyId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CurrencyNotFoundException.class, () -> {
            currencyService.updateCurrency(currencyId, name, code, null);
        });
    }

}

