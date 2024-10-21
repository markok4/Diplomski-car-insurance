package com.synechron.paymentservice.bank;

import com.synechron.paymentservice.dto.BankDTO;
import com.synechron.paymentservice.entity.Bank;
import com.synechron.paymentservice.mapper.BankDTOMapper;
import com.synechron.paymentservice.repository.BankRepository;
import com.synechron.paymentservice.service.BankService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BankServiceTest {
    @Mock
    BankDTOMapper bankMapper;
    @Mock
    BankRepository bankRepository;
    @InjectMocks
    BankService bankService;

    @Test
    public void whenGetAll_thenReturnsPageOfBankResponseDTO() {
        Pageable pageable = Pageable.unpaged();
        Bank bank = new Bank();
        BankDTO dto = new BankDTO();
        Page<Bank> countryPage = new PageImpl<>(Collections.singletonList(bank));

        given(bankRepository.findAll(pageable)).willReturn(countryPage);
        given(bankMapper.toDTO(bank)).willReturn(dto);

        Page<BankDTO> result = bankService.getAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertSame(dto, result.getContent().get(0));
    }

    @Test
    public void givenBankId_whenGetBankById_thenReturnBankObject(){
        Long bankId = 1l;
        Bank bank = new Bank();
        BankDTO dto = new BankDTO();

        given(bankRepository.findById(bankId)).willReturn(Optional.of(bank));
        given(bankMapper.toDTO(bank)).willReturn(dto);

        BankDTO result = bankService.getById(bankId);

        assertNotNull(result);
    }

    @Test
    public void givenNonExistingBankId_whenGetBankById_thenThrowEntityNotFoundException(){
        Long bankId = 1l;

        when(bankRepository.findById(bankId)).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(
                EntityNotFoundException.class,
                () -> bankService.getById(bankId),
                "Expected delete to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Entity does not exist"));
    }

    @Test
    public void givenBankDTO_whenUpdateBank_thenReturnUpdatedBankDTO(){
        Bank bank = new Bank();
        BankDTO dto = new BankDTO();
        dto.setId(1l);
        dto.setCountry("Serbia");
        dto.setEmployeeNumber(100);

        given(bankRepository.existsById(dto.getId())).willReturn(true);
        when(bankMapper.toEntity(dto)).thenReturn(bank);
        given(bankRepository.save(bank)).willReturn(bank);
        given(bankMapper.toDTO(bank)).willReturn(dto);

        BankDTO result = bankService.update(dto);

        assertNotNull(result);
        assertEquals("Serbia", result.getCountry());
        assertEquals(100, result.getEmployeeNumber());
    }

    @Test
    public void givenBankId_whenDeleteBank_thenSetIsDeletedToTrue(){
        Long bankId = 1l;
        Bank bank = new Bank();

        given(bankRepository.findById(bankId)).willReturn(Optional.of(bank));

        bankService.delete(bankId);

        assertTrue(bank.getIsDeleted());
        verify(bankRepository).save(bank);
    }

    @Test
    public void givenNonExistingBankId_whenDeleteBank_thenThrowEntityNotFoundException(){
        Long bankId = 1l;

        when(bankRepository.findById(bankId)).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(
                EntityNotFoundException.class,
                () -> bankService.delete(bankId),
                "Expected delete to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Entity with id " + bankId + " does not exist"));
    }

    @Test
    public void givenBankId_whenRestoreBank_thenSetIsDeletedToFalse(){
        Long bankId = 1l;
        Bank bank = new Bank();

        given(bankRepository.findById(bankId)).willReturn(Optional.of(bank));

        bankService.restore(bankId);

        assertFalse(bank.getIsDeleted());
        verify(bankRepository).save(bank);
    }

    @Test
    public void givenNonExistingBankId_whenRestoreBank_thenThrowEntityNotFoundException(){
        Long bankId = 1l;

        when(bankRepository.findById(bankId)).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(
                EntityNotFoundException.class,
                () -> bankService.restore(bankId),
                "Expected delete to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("Entity with id " + bankId + " does not exist"));
    }
}
