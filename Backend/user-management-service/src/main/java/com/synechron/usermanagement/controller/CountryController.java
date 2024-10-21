package com.synechron.usermanagement.controller;

import com.synechron.usermanagement.dto.CountryDTO;
import com.synechron.usermanagement.dto.CountryResponseDTO;
import com.synechron.usermanagement.service.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/countries")
@Tag(name = "Country")
public class CountryController {
    @Autowired
    private CountryService service;

    @Operation(summary = "Get all countries")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved countries",
                    content = @Content(mediaType = "application/json"))
    })
    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'MANAGER')")
    @GetMapping()
    public ResponseEntity<Page<CountryResponseDTO>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        {
            Page<CountryResponseDTO> countryDtoPage = service.getAll(PageRequest.of(page, size));
            return ResponseEntity.ok(countryDtoPage);
        }
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok("Country with ID " + id + " has been successfully deleted");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Country not found: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while deleting the country: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @PatchMapping("restore/{id}")
    public ResponseEntity<?> restore(@PathVariable Long id) {
        try {
            service.restore(id);
            return ResponseEntity.ok("Country with ID " + id + " has been successfully restored");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Country not found: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while restoring the country: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CountryDTO countryDTO) {
        try {
            CountryResponseDTO createdModelDTO = service.create(countryDTO);
            return new ResponseEntity<>(createdModelDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while creating the country: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody CountryDTO countryDTO) {
        try {
            CountryResponseDTO updatedCountry = service.update(id, countryDTO);
            return new ResponseEntity<>(updatedCountry, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while updating the model: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        {
            try {
                Optional<CountryResponseDTO> country = service.getById(id);
                if (country.isPresent()) {
                    return ResponseEntity.ok().body(country.get());
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Country not found");
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
            }
        }
    }
}
