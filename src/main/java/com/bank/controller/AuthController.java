package com.bank.controller;

import com.bank.dto.AuthRequest;
import com.bank.dto.AuthResponse;
import com.bank.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Marks this class as REST controller
@RequestMapping("/auth") // Base URL for auth endpoints
public class AuthController {
    private AuthenticationManager authenticationManager; // Used to verify username and password

    private JwtService jwtService; // Used to generate token

    @PostMapping("/login") // Creates POST /auth/login endpoint
    public AuthResponse login(@RequestBody AuthRequest request) { // Accepts username and password

        Authentication authentication = authenticationManager.authenticate( // Authenticates user credentials
                new UsernamePasswordAuthenticationToken( // Creates Spring auth token
                        request.getUsername(), // Reads username from request
                        request.getPassword() // Reads password from request
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal(); // Gets authenticated user details
        String token = jwtService.generateToken(userDetails); // Generates JWT token for user

        return new AuthResponse(token); // Returns token in response
    }
}
