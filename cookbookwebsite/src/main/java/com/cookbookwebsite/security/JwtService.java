package com.cookbookwebsite.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Create JWT
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
    public String extractUserEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}