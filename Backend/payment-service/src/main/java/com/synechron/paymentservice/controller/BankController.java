package com.synechron.paymentservice.controller;

import com.synechron.paymentservice.dto.UpdateInfo;
import com.synechron.paymentservice.dto.BankDTO;
import com.synechron.paymentservice.dto.CreateInfo;
import com.synechron.paymentservice.service.BankService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banks")
@Tag(name = "Bank")
@PreAuthorize("hasAuthority('ADMINISTRATOR')")
public class BankController {

    @Autowired
    private BankService service;

    @Operation(summary = "Get one bank")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Unable to get bank"),
            @ApiResponse(responseCode = "200", description = "Successfully retrieved bank",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        try{
            BankDTO bank = service.getById(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(bank);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while fetching the bank: " + e.getMessage());
        }
    }

    @Operation(summary = "Get all banks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Unable to get banks"),
            @ApiResponse(responseCode = "200", description = "Successfully retrieved banks",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping()
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size){
        try{
            Page<BankDTO> banks = service.getAll(PageRequest.of(page,size));
            return ResponseEntity.status(HttpStatus.OK)
                    .body(banks);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while fetching the banks: " + e.getMessage());
        }
    }


    @PatchMapping("{id}/restore")
    public ResponseEntity<?> restore(@PathVariable Long id){
        try{
            service.restore(id);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully restored bank");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error occurred while restoring the bank: " + e.getMessage());
        }
    }


    @Operation(summary = "Create new bank")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "500", description = "Unable to create bank"), @ApiResponse(responseCode = "201", description = "Successfully created bank",
                    content = @Content(mediaType = "application/json"))
    })

    @PostMapping()
    public ResponseEntity<?> create (@Validated(CreateInfo.class) @RequestBody BankDTO bank){
        try {
            BankDTO createdBank = service.create(bank);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(createdBank);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while creating the bank: " + e.getMessage());
        }
    }

    @Operation(summary = "Delete a bank")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Unable to delete bank"),
            @ApiResponse(responseCode = "200", description = "Successfully deleted bank",
                    content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete (@PathVariable Long id){
        try {
            service.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted bank");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error occurred while deleting the bank: " + e.getMessage());
        }
    }

    @Operation(summary = "Update a bank")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Unable to update bank"),
            @ApiResponse(responseCode = "200", description = "Successfully updated bank",
                    content = @Content(mediaType = "application/json"))
    })
    @PutMapping()
    public ResponseEntity<?> update (@Validated(UpdateInfo.class) @RequestBody BankDTO bank){
        try {
            BankDTO updatedBank = service.update(bank);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(updatedBank);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while updating the bank: " + e.getMessage());
        }
    }
}