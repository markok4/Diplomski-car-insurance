package com.synechron.paymentservice.service;

import com.synechron.paymentservice.dto.CurrencyCreateDto;
import com.synechron.paymentservice.dto.CurrencyDto;
import com.synechron.paymentservice.entity.Currency;
import com.synechron.paymentservice.exception.CurrencyNotFoundException;
import com.synechron.paymentservice.mapper.CurrencyMapper;
import com.synechron.paymentservice.repository.CurrencyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.beans.Transient;
import java.io.IOException;
import java.nio.file.*;
import java.util.Objects;
import java.util.Optional;

@Service
public class CurrencyService {
    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    CurrencyMapper currencyMapper;
    public Page<Currency> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return currencyRepository.findAll(pageable);
    }

    public Currency getById(Long id) {
        return currencyRepository.findById(id)
                .orElseThrow(() -> new CurrencyNotFoundException(id));
    }

    public void toggleDeletedStatus(Long currencyId) {
        Currency currency = currencyRepository.findById(currencyId)
                .orElseThrow(() -> new CurrencyNotFoundException(currencyId));
        currency.setIsDeleted(!currency.getIsDeleted()); // Toggle the isDeleted property
        currencyRepository.save(currency);
    }

    public CurrencyDto createCurrency(String name, String code, MultipartFile logo) throws IOException {
        Currency newCurrency = new Currency();
        newCurrency.setName(name);
        newCurrency.setCode(code);

        if (logo != null && !logo.isEmpty()) {
            String fileExtension = Objects.requireNonNull(logo.getOriginalFilename()).split("\\.")[1];
            String storedFileName = "currency_" + System.currentTimeMillis() + "." + fileExtension;
            Path uploadDir = Paths.get("../payment-service/src/main/resources/uploads/currency-logos");
            Path targetPath = uploadDir.resolve(storedFileName);

            Files.createDirectories(uploadDir);
            Files.write(targetPath, logo.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            newCurrency.setLogo(storedFileName); // Set the file path in the entity
        }

        Currency savedCurrency = currencyRepository.save(newCurrency);
        return currencyMapper.toDTO(savedCurrency);
    }

    @Transactional
    public CurrencyDto updateCurrency(Long id, String name, String code, MultipartFile logo) throws IOException {
        Currency currency = currencyRepository.findById(id)
                .orElseThrow(() -> new CurrencyNotFoundException(id));

        currency.setName(name);
        currency.setCode(code);

        if (logo != null && !logo.isEmpty()) {
            String fileExtension = Objects.requireNonNull(logo.getOriginalFilename()).split("\\.")[1];
            String storedFileName = "currency_" + System.currentTimeMillis() + "." + fileExtension;
            Path uploadDir = Paths.get("../payment-service/src/main/resources/uploads/currency-logos");
            Path targetPath = uploadDir.resolve(storedFileName);

            Files.createDirectories(uploadDir);
            Files.write(targetPath, logo.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            currency.setLogo(storedFileName); // Set the file path in the entity
        }

        currency = currencyRepository.save(currency); // Save the updated entity

        return currencyMapper.toDTO(currency); // Convert to DTO if necessary
    }

}