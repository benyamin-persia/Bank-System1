package com.bank.dto;

public class AuthResponse {
    private String token; // Stores generated JWT token

    public AuthResponse(String token) { // Constructor with token
        this.token = token; // Save token value
    }

    public String getToken() { // Returns token
        return token; // Return token value
    }

    public void setToken(String token) { // Sets token
        this.token = token; // Save token value
    }
}
