package com.synechron.policycreationservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.synechron.policycreationservice.dto.ProposalDTO;
import com.synechron.policycreationservice.dto.ProposalInitializeDTO;
import com.synechron.policycreationservice.dto.SetProposalSubscriberDTO;
import com.synechron.policycreationservice.service.ProposalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/proposals")
@PreAuthorize("hasAuthority('ADMINISTRATOR')")
public class ProposalController {

    @Autowired
    private ProposalService proposalService;

    @PreAuthorize("hasAnyAuthority('SALES_AGENT', 'ADMINISTRATOR')")
    @Operation(summary = "Get all proposals")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Unable to get proposals"),
            @ApiResponse(responseCode = "200", description = "Successfully retrieved proposals",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        try {
            Page<ProposalDTO> proposals = proposalService.getAll(PageRequest.of(page, size));
            return ResponseEntity.ok(proposals);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while fatching the proposals: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('SALES_AGENT', 'ADMINISTRATOR')")
    @Operation(summary = "Get all not deleted proposals")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Unable to get proposals"),
            @ApiResponse(responseCode = "200", description = "Successfully retrieved proposals",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/active")
    public ResponseEntity<?> getAllNotDeleted(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        try {
            Page<ProposalDTO> proposals = proposalService.getAllNotDeleted(PageRequest.of(page, size));
            return ResponseEntity.ok(proposals);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while fatching the proposals: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @Operation(summary = "Initializes empty proposal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created proposal",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/initialize")
    public ResponseEntity<?> create(@RequestBody ProposalInitializeDTO proposalData) {
        try {
            ProposalDTO proposal = proposalService.create(proposalData);
            return ResponseEntity.ok().body(proposal);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while initializing the proposal: " + e.getMessage());
        }
    }

    @Operation(summary = "Sets subscriber on a initialized proposal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully set subscriber",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Subscriber or proposal not found",
                    content = @Content(mediaType = "application/json"))
    })
    @PatchMapping("/set-subscriber")
    public ResponseEntity<?> setSubscriber(@RequestBody SetProposalSubscriberDTO proposalData) {
        try {
            ProposalDTO proposal = proposalService.setSubscriber(proposalData.getProposalId(), proposalData.getSubscriberId()).get();
            return ResponseEntity.ok().body(proposal);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (ExecutionException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while initializing the proposal: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            ProposalDTO proposal = proposalService.getById(id);
            return ResponseEntity.ok().body(proposal);
        } catch (EntityNotFoundException | JsonProcessingException ex) {
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }
}
