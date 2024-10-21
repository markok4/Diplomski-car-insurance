package com.synechron.paymentservice.bank;

import com.synechron.paymentservice.config.TestDatabaseConfig;
import com.synechron.paymentservice.entity.Bank;
import com.synechron.paymentservice.repository.BankRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = TestDatabaseConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)

public class BankRepositoryTest {
    @Autowired
    BankRepository bankRepository;

    @Test
    public void whenFindAll_thenReturnAllBanks() {
        List<Bank> banks = bankRepository.findAll();

        assertEquals(11, banks.size(), "Should return all banks");
    }

    @Test
    public void whenFindById_thenReturnEmptyForNonExistentBank() {
        Optional<Bank> foundBank = bankRepository.findById(99L);

        assertFalse(foundBank.isPresent(), "Bank should not be found");
    }

    @Test
    public void whenCreateBank_thenBankIsPersisted() {
        Bank newBank = new Bank();
        newBank.setBankName("New Bank");
        newBank.setLogo("Logo");
        newBank.setBankAdress("Address");
        newBank.setCountry("Country");
        newBank.setCity("City");
        newBank.setEmployeeNumber(20);
        newBank.setCreatedAt(LocalDateTime.now());
        newBank.setIsDeleted(false);
        newBank.setIsBankrupt(false);

        Bank savedBank = bankRepository.save(newBank);

        assertNotNull(savedBank.getId(), "Bank should have an ID after being saved");

        assertEquals("New Bank", savedBank.getBankName(), "Saved bank should have the correct name");
        assertFalse(savedBank.getIsDeleted(), "Saved bank should not be marked as deleted");
    }

}
