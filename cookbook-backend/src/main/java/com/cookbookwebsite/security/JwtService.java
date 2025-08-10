package com.cookbookwebsite.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret; // base64-encoded HMAC key (at least 256 bits for HS256)

    private SecretKey key;

    @PostConstruct
    public void init() {
        byte[] decodedKey = Base64.getDecoder().decode(secret);
        this.key = Keys.hmacShaKeyFor(decodedKey);
    }

    public String generateToken(String userEmail) {
        long now = System.currentTimeMillis();
        long exp = now + 86_400_000; // 24h

        return Jwts.builder()
                .setSubject(userEmail)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(exp))
                .signWith(key)
                .compact();
    }

    public String extractUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            final String email = extractUserEmail(token);
            return email.equalsIgnoreCase(userDetails.getUsername()) && !isTokenExpired(token);
        } catch (ExpiredJwtException ex) {
            return false; // expired
        } catch (Exception ex) {
            return false; // bad signature, malformed, etc.
        }
    }

    // --- helpers ---

    private boolean isTokenExpired(String token) {
        Date exp = extractClaim(token, Claims::getExpiration);
        return exp.before(new Date());
    }

    private <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return resolver.apply(claims);
    }
}