package com.bank.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}") // Reads expiration time from application.yaml
    private long jwtExpiration; // Stores expiration time in milliseconds

    public String generateToken(UserDetails userDetails) { // Generates a token for a user
        return Jwts.builder() // Starts building the token
                .setSubject(userDetails.getUsername()) // Stores username inside token
                .setIssuedAt(new Date(System.currentTimeMillis())) // Sets current time as issue time
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration)) // Sets token expiration time
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) // Signs token with secret key
                .compact(); // Converts token builder to string
    }

    public String extractUsername(String token) { // Extracts username from token
        return Jwts.parser() // Starts token parser
                .setSigningKey(getSignInKey()) // Sets signing key for validation
                .build() // Builds parser
                .parseClaimsJws(token) // Parses token
                .getBody() // Gets token body
                .getSubject(); // Returns username from subject
    }

    public boolean isTokenValid(String token, UserDetails userDetails) { // Checks if token is valid
        String username = extractUsername(token); // Extracts username from token
        Date expiration = Jwts.parser() // Starts token parser again
                .setSigningKey(getSignInKey()) // Sets signing key
                .build() // Builds parser
                .parseClaimsJws(token) // Parses token
                .getBody() // Gets token body
                .getExpiration(); // Gets expiration date

        return username.equals(userDetails.getUsername()) && expiration.after(new Date()); // Returns true if username matches and token not expired
    }

    private Key getSignInKey() { // Creates signing key from secret
        byte[] keyBytes = Decoders.BASE64.decode(secretKey); // Decodes Base64 secret string
        return Keys.hmacShaKeyFor(keyBytes); // Builds HMAC key from bytes
    }
}
