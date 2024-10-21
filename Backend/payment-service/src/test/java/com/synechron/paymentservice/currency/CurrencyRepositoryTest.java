package com.synechron.paymentservice.currency;

import com.synechron.paymentservice.config.TestDatabaseConfig;
import com.synechron.paymentservice.entity.Currency;
import com.synechron.paymentservice.repository.CurrencyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;


import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@ContextConfiguration(classes = TestDatabaseConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)

public class CurrencyRepositoryTest {
    @Autowired
    private CurrencyRepository currencyRepository;

    @Test
    public void whenFindAll_thenReturnAllCurrencies() {
        List<Currency> currencies = currencyRepository.findAll();

        assertEquals(7, currencies.size(), "Should return all currencies");
    }

    @Test
    public void whenFindById_thenReturnEmptyForNonExistentCurrency() {
        Optional<Currency> foundCurrency = currencyRepository.findById(99L);

        assertFalse(foundCurrency.isPresent(), "Currency should not be found");
    }

    @Test
    public void whenCreateCurrency_thenCurrencyIsPersisted() {
        Currency newCurrency = new Currency();
        newCurrency.setName("New Currency");
        newCurrency.setCode("NWC");
        newCurrency.setLogo("new_currency_logo.png");
        newCurrency.setIsDeleted(false);
        newCurrency.setIsValid(true);

        Currency savedCurrency = currencyRepository.save(newCurrency);

        assertNotNull(savedCurrency.getId(), "Currency should have an ID after being saved");
        assertEquals("New Currency", savedCurrency.getName(), "Saved currency should have the correct name");
        assertEquals("NWC", savedCurrency.getCode(), "Saved currency should have the correct code");
        assertFalse(savedCurrency.getIsDeleted(), "Saved currency should not be marked as deleted");
        assertEquals(true, savedCurrency.getIsValid(), "Saved currency should be marked as valid");
    }




}
