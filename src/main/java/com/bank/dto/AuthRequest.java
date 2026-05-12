package com.bank.dto;

public class AuthRequest {
    private String username; // Stores username from request
    private String password; // Stores password from request

    public String getUsername() { // Returns username
        return username; // Return username value
    }

    public void setUsername(String username) { // Sets username
        this.username = username; // Save username value
    }

    public String getPassword() { // Returns password
        return password; // Return password value
    }

    public void setPassword(String password) { // Sets password
        this.password = password; // Save password value
    }
}
