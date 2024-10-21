package com.synechron.paymentservice.controller;

import com.synechron.paymentservice.dto.CurrencyCreateDto;
import com.synechron.paymentservice.dto.CurrencyDto;
import com.synechron.paymentservice.dto.UpdateInfo;
import com.synechron.paymentservice.entity.Currency;
import com.synechron.paymentservice.exception.CurrencyNotFoundException;
import com.synechron.paymentservice.mapper.CurrencyMapper;
import com.synechron.paymentservice.service.CurrencyService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Validated
@RestController
@RequestMapping("/currencies")
@Tag(name = "Currency")
@PreAuthorize("hasAuthority('ADMINISTRATOR')")
public class CurrencyController {
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private CurrencyMapper currencyMapper;

    @Operation(summary = "Get all currencies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved currencies",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping
    public ResponseEntity<Page<CurrencyDto>> getAll(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) {
        Page<Currency> currencies = currencyService.getAll(page, size);
        Page<CurrencyDto> currencyDtos = currencies.map(currencyMapper::toDTO);
        return new ResponseEntity<>(currencyDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CurrencyDto> getById(@PathVariable Long id) {
        try {
            Currency currency = currencyService.getById(id);
            return new ResponseEntity<>(currencyMapper.toDTO(currency), HttpStatus.OK);
        } catch (CurrencyNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception ex) {
            // Add additional error handling as needed
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}/toggle-deleted")
    public ResponseEntity<?> toggleDeletedStatus(@PathVariable Long id) {
        try {
            currencyService.toggleDeletedStatus(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (CurrencyNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<?> create(
            @RequestPart("name") String name,
            @RequestPart("code") String code,
            @RequestPart("logo") MultipartFile logo) {
        try {
            CurrencyDto createdCurrencyDto = currencyService.createCurrency(name, code, logo);
            return new ResponseEntity<>(createdCurrencyDto, HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while creating the currency: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("id") Long id,
            @RequestPart("name") String name,
            @RequestPart("code") String code,
            @RequestPart(value = "logo", required = false) MultipartFile logo){
        try {
            CurrencyDto updatedCurrency = currencyService.updateCurrency(id, name, code, logo);
            return ResponseEntity.ok(updatedCurrency);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while updating the bank: " + e.getMessage());
        }
    }

    @GetMapping("/logos/{imageName:.+}")
    public ResponseEntity<?> getCurrencyLogo(@PathVariable String imageName) {
        try {
            Path imagePath = Paths.get("../payment-service/src/main/resources/uploads/currency-logos/", imageName);
            byte[] imageBytes = Files.readAllBytes(imagePath);
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(base64Image);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while retrieving the currency logo: " + e.getMessage());
        }
    }

}
