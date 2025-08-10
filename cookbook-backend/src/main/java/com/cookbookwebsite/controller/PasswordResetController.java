package com.cookbookwebsite.controller;

import com.cookbookwebsite.model.User;
import com.cookbookwebsite.model.PasswordResetToken;
import com.cookbookwebsite.service.EmailService;
import com.cookbookwebsite.service.PasswordResetTokenService;
import com.cookbookwebsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/password-reset")
public class PasswordResetController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordResetTokenService tokenService;

    @Autowired
    private EmailService emailService;

    // Step 1: Request reset (send token to email)
    @PostMapping("/request")
    public ResponseEntity<String> requestReset(@RequestBody EmailRequest request) {
        Optional<User> userOpt = userService.findByEmail(request.getEmail());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("No user with that email");
        }

        PasswordResetToken token = tokenService.createToken(userOpt.get());

        String link = "localhost:5173/reset-password?token=" + token.getToken();
        emailService.sendEmail(
                userOpt.get().getUserEmail(),
                "Password Reset Request",
                "Click this link to reset your password: " + link
        );
        return ResponseEntity.ok("Reset link sent to email.");
    }

    // Step 2: Confirm token is valid
    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestParam String token) {
        Optional<PasswordResetToken> validToken = tokenService.validateToken(token);
        if (validToken.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid or expired token");
        }
        return ResponseEntity.ok("Token is valid");
    }

    // Step 3: Actually reset the password
    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        Optional<PasswordResetToken> validToken = tokenService.validateToken(request.getToken());
        if (validToken.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid or expired token");
        }

        User user = validToken.get().getUser();
        userService.updatePassword(user, request.getNewPassword());
        tokenService.deleteToken(request.getToken());

        return ResponseEntity.ok("Password reset successful");
    }

    // Request body DTOs
    public static class EmailRequest {
        private String email;
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }

    public static class ResetPasswordRequest {
        private String token;
        private String newPassword;
        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
        public String getNewPassword() { return newPassword; }
        public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
    }
}