package com.cookbookwebsite.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

// Handles generation and validation of JWTs.
@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    private SecretKey key;

    // Converts the secret from application.properties to a cryptographic key.
    @PostConstruct
    public void init() {
        byte[] decodedKey = Base64.getDecoder().decode(secret);
        this.key = Keys.hmacShaKeyFor(decodedKey);
    }

    // Create JWT
    // Signs a JWT with subject = email, expiration = 24h, and the secret key.
    public String generateToken(String userEmail) {
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + 86400000; // Token valid for 1 day

        return Jwts.builder()
                .setSubject(userEmail)
                .setIssuedAt(new Date(nowMillis))
                .setExpiration(new Date(expMillis))
                .signWith(key)
                .compact();
    }

    // Validate and parse JWT
    // Verifies the token and extracts the email (which we use to look up the user).
    public String extractUserEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}