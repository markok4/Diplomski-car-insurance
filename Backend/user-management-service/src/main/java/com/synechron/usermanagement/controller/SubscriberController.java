package com.synechron.usermanagement.controller;

import com.synechron.usermanagement.dto.SubscriberRegisterDTO;
import com.synechron.usermanagement.dto.SubscriberResponseDTO;
import com.synechron.usermanagement.dto.SubscriberRoleDTO;
import com.synechron.usermanagement.service.SubscriberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/subscribers")
@Tag(name = "Subscriber Management")
public class SubscriberController {

    @Autowired
    private SubscriberService service;

    @Operation(summary = "Get all subscribers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "No subscribers found"),
            @ApiResponse(responseCode = "200", description = "Successfully retrieved subscribers",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping()
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        Page<SubscriberResponseDTO> subscriberDtoPage = service.getAll(PageRequest.of(page, size));
        return ResponseEntity.ok(subscriberDtoPage);
    }

    @Operation(summary = "Get subscriber by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Subscriber not found"),
            @ApiResponse(responseCode = "200", description = "Successfully retrieved subscriber",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {

            SubscriberResponseDTO subscriberDtoPage = service.getById(id);
            return ResponseEntity.ok(subscriberDtoPage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subscriber not found");
        }
    }

    @Operation(summary = "Create subscriber")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully registered subscriber",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping()
    public ResponseEntity<?> register(@RequestBody SubscriberRegisterDTO dto) {
        try {
            SubscriberResponseDTO user = service.register(dto);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(user);
        } catch (Error e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while registering: " + e.getMessage());
        }
    }

    @Operation(summary = "Gets all subscriber roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched subscriber roles",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/roles")
    public ResponseEntity<?> getRoles() {
        try {
            List<SubscriberRoleDTO> roles = service.getRoles();

            return ResponseEntity.status(HttpStatus.OK)
                    .body(roles);
        } catch (Error e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while fetching roles: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(required = false) String credentials) {
        try {
            List<SubscriberResponseDTO> subscribers = service.search(page, size, credentials);
            return ResponseEntity.ok(new PageImpl<>(subscribers, PageRequest.of(page, size), subscribers.size()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro occured while searching subscribers: " + e.getMessage());
        }
    }
}
