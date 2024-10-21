package com.synechron.usermanagement.controller;

import com.synechron.usermanagement.dto.UserResponseDTO;
import com.synechron.usermanagement.dto.UserRegisterDTO;

import com.synechron.usermanagement.dto.*;
import com.synechron.usermanagement.model.Gender;
import com.synechron.usermanagement.model.MaritialStatus;

import com.synechron.usermanagement.model.User;
import com.synechron.usermanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessagingException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Base64;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@Tag(name = "User Management")
public class UserController {
    @Autowired
    private UserService service;

    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "No users found"),
            @ApiResponse(responseCode = "200", description = "Successfully retrieved users",
                    content = @Content(mediaType = "application/json"))
    })

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<User> users = service.getAll();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getByEmail(@PathVariable String email) {
        {
            try {
                UserResponseDTO user = service.getByEmail(email);
                if (user!=null) {
                    return ResponseEntity.ok().body(user);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
            }
        }
    }

    @PatchMapping("/update-profile/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String jmbg,
            @RequestParam String birth,
            @RequestParam String gender,
            @RequestParam String maritialStatus,
            @RequestPart MultipartFile profileImage
    ) {
        try {
            UserProfileUpdateDTO userProfileUpdateDTO = UserProfileUpdateDTO.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .jmbg(jmbg)
                    .birth(LocalDateTime.parse(birth))
                    .gender(Gender.valueOf(gender))
                    .maritialStatus(MaritialStatus.valueOf(maritialStatus))
                    .profileImage(profileImage)
                    .build();

            UserResponseDTO userResponseDTO = service.update(id, userProfileUpdateDTO);
            return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while updating the user profile: " + e.getMessage());
        }
    }

    @Operation(summary = "Register user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully registered user",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDTO dto){
        try{
            User user = service.register(dto);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(user);
        } catch(Error e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while registering: " + e.getMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/profile-image/{imageName:.+}")
    public ResponseEntity<?> getProfileImage(@PathVariable String imageName) {
        try {
            Path imagePath = Paths.get("../user-management-service/src/main/resources/uploads/images/", imageName);
            byte[] imageBytes = Files.readAllBytes(imagePath);
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<>(base64Image, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while retrieving the profile image: " + e.getMessage());
        }
    }

}

