package com.synechron.carservice.controller;

import com.synechron.carservice.dto.CarPartDTO;
import com.synechron.carservice.service.CarPartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/car-parts")
@Tag(name = "CarPart")
@PreAuthorize("hasAuthority('ADMINISTRATOR')")
public class CarPartController {
    @Autowired
    private CarPartService carPartService;

    @Operation(summary = "Get all car parts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "No car parts found"),
            @ApiResponse(responseCode = "200", description = "Successfully retrieved car parts",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping
    public ResponseEntity<Page<CarPartDTO>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<CarPartDTO> carPartDTOPage = carPartService.getAll(PageRequest.of(page, size));
        return ResponseEntity.ok(carPartDTOPage);
    }
}
