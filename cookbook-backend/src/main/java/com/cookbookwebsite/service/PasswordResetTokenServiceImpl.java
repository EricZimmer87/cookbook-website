package com.cookbookwebsite.service;

import com.cookbookwebsite.model.PasswordResetToken;
import com.cookbookwebsite.model.User;
import com.cookbookwebsite.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Override
    @Transactional
    public PasswordResetToken createToken(User user) {
        tokenRepository.deleteByUser(user);
        PasswordResetToken token = new PasswordResetToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(LocalDateTime.now().plusHours(1));
        return tokenRepository.save(token);
    }

    @Override
    public Optional<PasswordResetToken> validateToken(String token) {
        Optional<PasswordResetToken> optionalToken = tokenRepository.findByToken(token);
        if (optionalToken.isPresent()) {
            PasswordResetToken resetToken = optionalToken.get();
            if (resetToken.getExpiryDate().isAfter(LocalDateTime.now())) {
                return optionalToken;
            }
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void deleteToken(String token) {
        tokenRepository.deleteByToken(token);
    }
}