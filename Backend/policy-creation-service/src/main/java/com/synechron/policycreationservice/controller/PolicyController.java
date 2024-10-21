package com.synechron.policycreationservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.synechron.policycreationservice.dto.PolicyDTO;
import com.synechron.policycreationservice.mapper.PolicyMapper;
import com.synechron.policycreationservice.model.Policy;
import com.synechron.policycreationservice.service.PolicyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/policies")
@Tag(name = "Policy")
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    @Autowired
    private PolicyMapper policyMapper;

    @Operation(summary = "Get all policies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "No policies found"),
            @ApiResponse(responseCode = "200", description = "Successfully retrieved policies",
                    content = @Content(mediaType = "application/json"))
    })
    @PreAuthorize("hasAnyAuthority('SALES_AGENT', 'ADMINISTRATOR', 'SUBSCRIBER')") // Different roles allowed
    @GetMapping
    public ResponseEntity<Page<PolicyDTO>> getAll(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size,
                                                  @RequestParam(defaultValue = "") String sortBy) {
        Page<Policy> policies = policyService.getAll(page, size, sortBy);
        Page<PolicyDTO> policyDTOS = policies.map(policyMapper::toDTO);
        return new ResponseEntity<>(policyDTOS, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('SALES_AGENT')")
    @GetMapping("/by-sales-agent")
    public ResponseEntity<Page<PolicyDTO>> getPoliciesBySalesAgentEmail(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam String salesAgentEmail,
            @RequestParam(defaultValue = "") String sortBy) {

        Page<Policy> policies = policyService.getPoliciesBySalesAgentEmail(page, size, salesAgentEmail, sortBy);
        Page<PolicyDTO> policyDTOS = policies.map(policyMapper::toDTO);
        return new ResponseEntity<>(policyDTOS, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('SALES_AGENT', 'ADMINISTRATOR', 'SUBSCRIBER')")
    @Operation(summary = "Get one policy")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Unable to get policy"),
            @ApiResponse(responseCode = "200", description = "Successfully retrieved policy",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            PolicyDTO policy = policyService.getById(id);
            return ResponseEntity.ok().body(policy);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.internalServerError().body("Error occurred while fetching the policy: " + ex.getMessage());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/by-subscriber")
    @PreAuthorize("hasAnyAuthority('SALES_AGENT', 'ADMINISTRATOR', 'SUBSCRIBER')")
    public ResponseEntity<?> getAllPolicies(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size) {
        try {
            CompletableFuture<Page<PolicyDTO>> policiesFuture = policyService.sendEmailToUserService(page, size);
            Page<PolicyDTO> policies = policiesFuture.get();
            System.out.println("Policies: " + policies.getContent());
            return ResponseEntity.ok(policies);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occured while fetching the policies: " + e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(required = false) String nameOrEmail,
                                    @RequestParam(required = false) Integer year,
                                    @RequestParam(required = false) Integer month,
                                    @RequestParam(required = false) Integer day,
                                    @RequestParam(required = false) Long brandId,
                                    @RequestParam(required = false) Long modelId,
                                    @RequestParam(required = false) Long carYear) {
        try {
            List<PolicyDTO> policies = policyService.search(page, size, nameOrEmail, year == null ? null : LocalDate.of(year, month, day), brandId, modelId, carYear).get();
            return ResponseEntity.ok(new PageImpl<>(policies, PageRequest.of(page, size), policies.size()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro occured while searching policies: " + e.getMessage());
        }
    }
}