package com.synechron.usermanagement.auth.controller;

import jakarta.validation.Valid;
import org.springframework.security.core.GrantedAuthority;
import com.synechron.usermanagement.auth.dto.AuthRequestDTO;
import com.synechron.usermanagement.auth.dto.JWTResponseDTO;
import com.synechron.usermanagement.auth.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @PostMapping("/login")
    public JWTResponseDTO authenticateAndGetToken(@Valid @RequestBody AuthRequestDTO authRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword())
        );
        if(authentication.isAuthenticated()){
            String username = authentication.getName();
            String userRole = null;
            if (!authentication.getAuthorities().isEmpty()) {
                userRole = authentication.getAuthorities().iterator().next().getAuthority();
            }
            String accessToken = jwtService.GenerateToken(username, userRole);
            return JWTResponseDTO.builder()
                    .accessToken(accessToken)
                    .build();
        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }
}
