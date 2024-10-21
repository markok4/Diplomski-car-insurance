package com.synechron.paymentservice.service;
import com.synechron.paymentservice.entity.Bank;
import com.synechron.paymentservice.repository.BankRepository;
import jakarta.persistence.EntityNotFoundException;
import com.synechron.paymentservice.dto.BankDTO;
import com.synechron.paymentservice.mapper.BankDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@Service
public class BankService {
    @Autowired
    private BankRepository repository;
    @Autowired
    private BankDTOMapper dtoMapper;

    public BankDTO getById(Long id){
        try{
            Optional<Bank> result = repository.findById(id);
            if (result.isPresent()) {
                return dtoMapper.toDTO(result.get());
            } else {
                throw new EntityNotFoundException("Entity does not exist");
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }
    }

    public Page<BankDTO> getAll(Pageable pageable) {
        try{
            Page<Bank> bankPage = repository.findAll(pageable);
            return bankPage.map(bank->dtoMapper.toDTO(bank));
        }catch (Exception e){
            System.out.println(e);
            throw e;
        }
    }

    public BankDTO create(BankDTO bank){
        try{
            bank.setId(null);
            bank.setCreatedAt(LocalDateTime.now());
            bank.setIsDeleted(false);
            bank.setIsBankrupt(false);
            return dtoMapper.toDTO(repository.save(dtoMapper.toEntity(bank)));
        } catch(Exception e){
            System.out.println(e);
            throw e;
        }
    }

    public BankDTO update(BankDTO bank){
        try{
            if (repository.existsById(bank.getId())) {
                return dtoMapper.toDTO(repository.save(dtoMapper.toEntity(bank)));
            } else {
                throw new EntityNotFoundException("Entity does not exist");
            }
        }catch(Exception e){
            System.out.println(e);
            throw e;
        }
    }

    public void delete(Long id) {
        Optional<Bank> result = repository.findById(id);
        if (result.isPresent()) {
            result.get().setIsDeleted(true);
            repository.save(result.get());
        } else {
            throw new EntityNotFoundException("Entity with id " + id + " does not exist");
        }
    }

    public void restore(Long id) {
        Optional<Bank> result = repository.findById(id);
        if (result.isPresent()) {
            result.get().setIsDeleted(false);
            repository.save(result.get());
        } else {
            // Throw a custom exception or handle the not found case
            throw new EntityNotFoundException("Entity with id " + id + " does not exist");
        }
    }
}
