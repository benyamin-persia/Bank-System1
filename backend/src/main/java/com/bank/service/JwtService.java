package com.bank.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}") // Reads expiration time from application.yaml
    private long jwtExpiration; // Stores expiration time in milliseconds

    public String generateToken(UserDetails userDetails) { // Generates a token for a user
        return Jwts.builder() // Starts building the token
                .subject(userDetails.getUsername()) // Stores username inside token
                .issuedAt(new Date(System.currentTimeMillis())) // Sets current time as issue time
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration)) // Sets token expiration time
                .signWith(getSignInKey(), Jwts.SIG.HS256) // Signs token with the modern JJWT algorithm API
                .compact(); // Converts token builder to string
    }

    public String extractUsername(String token) { // Extracts username from token
        return extractClaims(token).getSubject(); // Returns username from parsed token claims
    }

    public boolean isTokenValid(String token, UserDetails userDetails) { // Checks if token is valid
        Claims claims = extractClaims(token); // Parse once so username and expiration come from the same verified payload
        String username = claims.getSubject(); // Extracts username from token
        Date expiration = claims.getExpiration(); // Gets expiration date from verified claims

        return username.equals(userDetails.getUsername()) && expiration.after(new Date()); // Returns true if username matches and token not expired
    }

    private Claims extractClaims(String token) { // Parses and verifies token claims with the configured signing key
        return Jwts.parser() // Starts token parser
                .verifyWith(getSignInKey()) // Sets signing key for validation using the modern JJWT API
                .build() // Builds parser
                .parseSignedClaims(token) // Parses signed token
                .getPayload(); // Gets token claims payload
    }

    private SecretKey getSignInKey() { // Creates signing key from secret
        byte[] keyBytes = Decoders.BASE64.decode(secretKey); // Decodes Base64 secret string
        return Keys.hmacShaKeyFor(keyBytes); // Builds HMAC key from bytes
    }
}
