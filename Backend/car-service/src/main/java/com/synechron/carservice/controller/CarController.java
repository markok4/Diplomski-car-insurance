package com.synechron.carservice.controller;
//todo 3 dto klase imamo

import com.synechron.carservice.dto.CarCreateDTO;
import com.synechron.carservice.dto.CarDTO2;
import com.synechron.carservice.dto.CarResponseDTO;

import com.synechron.carservice.dto.CarDTO;
import com.synechron.carservice.model.Car;
import com.synechron.carservice.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Year;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/cars")
@PreAuthorize("hasAuthority('ADMINISTRATOR')")
public class CarController {

    @Autowired
    private CarService carService;

    @Operation(summary = "Get all cars")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Unable to create car"),
            @ApiResponse(responseCode = "200", description = "Successfully retrieved cars",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        try {
            Page<CarDTO> cars = carService.getAll(PageRequest.of(page, size));
            return ResponseEntity.ok(cars);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occured while fatching the cars: " + e.getMessage());
        }
    }


    @Operation(summary = "Creates new car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "No cars found"),
            @ApiResponse(responseCode = "201", description = "Successfully created car",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping()
    public ResponseEntity<?> create(@RequestParam("year") int year, @RequestParam("modelId") long modelId, @RequestParam("carPartIds") long[] carPartIds, @RequestParam("image") MultipartFile image) {
        try {
            CarCreateDTO carCreateDTO = CarCreateDTO.builder()
                    .modelId(modelId)
                    .carPartIds(carPartIds)
                    .year(Year.of(year))
                    .image(image)
                    .build();
            CarDTO2 car = carService.create(carCreateDTO);
            return ResponseEntity.ok().body(car);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while creating the car: " + e.getMessage());
        }
    }

    @GetMapping("/images/{imageName:.+}")
    public ResponseEntity<?> getCarImage(@PathVariable String imageName) {
        try {
            Path imagePath = Paths.get("../car-service/src/main/resources/uploads/cars/", imageName);
            byte[] imageBytes = Files.readAllBytes(imagePath);
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(base64Image);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while retrieving the currency logo: " + e.getMessage());
        }
    }

    @Operation(summary = "Delete a car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Unable to delete car"),
            @ApiResponse(responseCode = "200", description = "Successfully deleted car",
                    content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            carService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted car");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error occurred while deleting the car: " + e.getMessage());
        }
    }

    @PatchMapping("{id}/restore")
    public ResponseEntity<?> restore(@PathVariable Long id) {
        try {
            carService.restore(id);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully restored car");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error occurred while restoring the car: " + e.getMessage());
        }
    }

    @Operation(summary = "Get one car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Unable to get car"),
            @ApiResponse(responseCode = "200", description = "Successfully retrieved car",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            CarDTO car = carService.getById(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(car);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while fetching the car: " + e.getMessage());
        }
    }
}
