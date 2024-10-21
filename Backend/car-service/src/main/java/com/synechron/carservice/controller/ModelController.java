package com.synechron.carservice.controller;


import com.synechron.carservice.dto.ModelCreateDTO;
import com.synechron.carservice.dto.ModelDTO;
import com.synechron.carservice.dto.ModelUpdateDTO;
import com.synechron.carservice.mapper.ModelDTOMapper;
import com.synechron.carservice.model.Model;
import com.synechron.carservice.service.ModelService;
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

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/models")
@Tag(name = "Model")
@PreAuthorize("hasAuthority('ADMINISTRATOR')")
public class ModelController {

    @Autowired
    private ModelService modelService;

    @Autowired
    private ModelDTOMapper modelDTOMapper;

    @Operation(summary = "Update a model")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Unable to update model"),
            @ApiResponse(responseCode = "404", description = "Model not found"),
            @ApiResponse(responseCode = "200", description = "Successfully updated model",
                    content = @Content(mediaType = "application/json"))
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody ModelUpdateDTO modelUpdateDTO) {
        try {
            Model updatedModel = modelService.update(id, modelUpdateDTO);
            ModelDTO updatedModelDTO = modelDTOMapper.toDTO(updatedModel);
            return new ResponseEntity<>(updatedModelDTO, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while updating the model: " + e.getMessage());
        }
    }

    @Operation(summary = "Create new model")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Unable to create model"),
            @ApiResponse(responseCode = "201", description = "Successfully created model",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ModelCreateDTO modelCreateDTO) {
        try {
            Model createdModel = modelService.create(modelCreateDTO);
            ModelDTO createdModelDTO = modelDTOMapper.toDTO(createdModel);
            return new ResponseEntity<>(createdModelDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while creating the model: " + e.getMessage());
        }
    }

    @Operation(summary = "Delete a model")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Unable to delete model"),
            @ApiResponse(responseCode = "404", description = "Model not found"),
            @ApiResponse(responseCode = "200", description = "Successfully deleted model",
                    content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            modelService.delete(id);
            return ResponseEntity.ok("Model with ID " + id + " has been successfully deleted");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Model not found: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while deleting the model: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> restore(@PathVariable Long id) {
        try {
            modelService.restore(id);
            return ResponseEntity.ok("Model with ID " + id + " has been successfully restore");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Model not found: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while restoring the model: " + e.getMessage());
        }
    }

    @Operation(summary = "Get one model")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Unable to get model"),
            @ApiResponse(responseCode = "404", description = "Model not found"),
            @ApiResponse(responseCode = "200", description = "Successfully retrieved model",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            Optional<ModelDTO> model = modelService.getById(id);
            if (model.isPresent()) {
                return ResponseEntity.ok().body(model.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Model not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @Operation(summary = "Get all models")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "No models found"),
            @ApiResponse(responseCode = "200", description = "Successfully retrieved models",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping
    public ResponseEntity<Page<ModelDTO>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ModelDTO> modelDTOPage = modelService.getAll(PageRequest.of(page, size));
        return ResponseEntity.ok(modelDTOPage);
    }

    @Operation(summary = "Get all models of brand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "No models found"),
            @ApiResponse(responseCode = "200", description = "Successfully retrieved models",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/brand/{brandId}")
    public ResponseEntity<List<ModelDTO>> getAllByBrand(@PathVariable long brandId) {
        List<ModelDTO> modelDTOPage = modelService.getAllByBrand(brandId);
        return ResponseEntity.ok(modelDTOPage);
    }
}
