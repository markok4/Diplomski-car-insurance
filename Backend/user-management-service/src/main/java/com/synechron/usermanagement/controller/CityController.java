package com.synechron.usermanagement.controller;

import com.synechron.usermanagement.dto.CityResponseDTO;
import com.synechron.usermanagement.dto.CountryResponseDTO;
import com.synechron.usermanagement.service.CityService;
import com.synechron.usermanagement.service.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cities")
@Tag(name = "City")
public class CityController {
    @Autowired
    private CityService service;

    @Operation(summary = "Get cities by country")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved cities",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/country/{countryId}")
    public ResponseEntity<Page<CityResponseDTO>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @PathVariable Long countryId
    ){
        {
            Page<CityResponseDTO> cityDto = service.getByCountry(PageRequest.of(page,size), countryId);
            return ResponseEntity.ok(cityDto);
        }
    }
}
