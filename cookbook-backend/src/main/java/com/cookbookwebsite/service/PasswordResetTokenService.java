package com.cookbookwebsite.service;

import com.cookbookwebsite.model.PasswordResetToken;
import com.cookbookwebsite.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface PasswordResetTokenService {
    PasswordResetToken createToken(User user);
    Optional<PasswordResetToken> validateToken(String token);
    void deleteToken(String token);
}