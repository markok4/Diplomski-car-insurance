package com.synechron.usermanagement.controller;

import com.synechron.usermanagement.dto.AddressResponseDTO;
import com.synechron.usermanagement.dto.ZipResponseDTO;
import com.synechron.usermanagement.service.AddressService;
import com.synechron.usermanagement.service.ZipService;
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
@RequestMapping("/zips")
@Tag(name = "Zip")
public class ZipController {

    @Autowired
    private ZipService service;

    @Operation(summary = "Get zip by city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved zips",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/city/{cityId}")
    public ResponseEntity<Page<ZipResponseDTO>> getByAddress(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @PathVariable Long cityId
    ){
        {
            Page<ZipResponseDTO> zipDto = service.getByCity(PageRequest.of(page,size), cityId);
            return ResponseEntity.ok(zipDto);
        }
    }
}
