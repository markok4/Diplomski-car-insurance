package com.synechron.usermanagement.controller;

import com.synechron.usermanagement.dto.AddressResponseDTO;
import com.synechron.usermanagement.dto.CityResponseDTO;
import com.synechron.usermanagement.service.AddressService;
import com.synechron.usermanagement.service.CityService;
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
@RequestMapping("/addresses")
@Tag(name = "Address")
public class AddressController {
    @Autowired
    private AddressService service;

    @Operation(summary = "Get addresses by city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved cities",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/city/{cityId}")
    public ResponseEntity<Page<AddressResponseDTO>> getByCity(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @PathVariable Long cityId
    ){
        {
            Page<AddressResponseDTO> addressDto = service.getByCity(PageRequest.of(page,size), cityId);
            return ResponseEntity.ok(addressDto);
        }
    }
}
