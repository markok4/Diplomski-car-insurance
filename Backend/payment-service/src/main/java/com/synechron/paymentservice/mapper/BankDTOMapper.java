package com.synechron.paymentservice.mapper;

import com.synechron.paymentservice.dto.BankDTO;
import com.synechron.paymentservice.entity.Bank;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BankDTOMapper implements IMapper<Bank, BankDTO> {
    @Override
    public BankDTO toDTO(Bank bank) {
        BankDTO dto = new BankDTO();

        dto.setId(bank.getId());
        dto.setName(bank.getBankName());
        dto.setLogo(bank.getLogo());
        dto.setCountry(bank.getCountry());
        dto.setCity(bank.getCity());
        dto.setAddress(bank.getBankAdress());
        dto.setEmployeeNumber(bank.getEmployeeNumber());
        dto.setIsDeleted(bank.getIsDeleted());
        dto.setIsBankrupt(bank.getIsBankrupt());
        dto.setCreatedAt(bank.getCreatedAt());

        return dto;
    }

    @Override
    public Bank toEntity(BankDTO dto) {
        Bank bank = new Bank();

        bank.setId(dto.getId());
        bank.setBankName(dto.getName());
        bank.setLogo(dto.getLogo());
        bank.setCity(dto.getCity());
        bank.setCountry(dto.getCountry());
        bank.setBankAdress(dto.getAddress());
        bank.setEmployeeNumber(dto.getEmployeeNumber());
        bank.setIsDeleted(dto.getIsDeleted());
        bank.setIsBankrupt(dto.getIsBankrupt());
        bank.setCreatedAt(dto.getCreatedAt());

        return bank;
    }

    @Override
    public List<BankDTO> listToDTO(List<Bank> banks) {
        List<BankDTO> dtos = new ArrayList<>();
        for (Bank bank : banks) {
            dtos.add(toDTO(bank));
        }
        return dtos;
    }
}
