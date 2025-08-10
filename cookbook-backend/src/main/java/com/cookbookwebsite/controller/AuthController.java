package com.cookbookwebsite.controller;

import com.cookbookwebsite.dto.auth.LoginRequest;
import com.cookbookwebsite.dto.auth.LoginResponse;
import com.cookbookwebsite.dto.auth.RegisterRequest;
import com.cookbookwebsite.dto.user.UserDTO;
import com.cookbookwebsite.model.Role;
import com.cookbookwebsite.model.User;
import com.cookbookwebsite.repository.RoleRepository;
import com.cookbookwebsite.repository.UserRepository;
import com.cookbookwebsite.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        if (userRepository.findByUserEmail(request.getUserEmail()).isPresent()) {
            return "Error: Email already in use.";
        }

        Role userRole = roleRepository.findByRoleId(3)
                .orElseThrow(() -> new RuntimeException("Default role not found"));

        User user = new User();
        user.setUserName(request.getUserName());
        user.setUserEmail(request.getUserEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(userRole);

        userRepository.save(user);

        return "User registered successfully!";
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        User user = userRepository.findByUserEmail(request.getUserEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid email or password.");
        }

        String token = jwtService.generateToken(user.getUserEmail());
        UserDTO userDTO = new UserDTO(user);

        return new LoginResponse(userDTO, token);
    }

    // Change password
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @RequestBody ChangePasswordRequest req,
            Principal principal // comes from JWT-authenticated request
    ) {
        // make sure this endpoint is NOT permitAll in SecurityConfig
        var user = userRepository.findByUserEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(req.getCurrentPassword(), user.getPasswordHash())) {
            return ResponseEntity.badRequest().body("Current password is incorrect.");
        }

        user.setPasswordHash(passwordEncoder.encode(req.getNewPassword()));
        userRepository.save(user);

        // (optional) invalidate existing JWTs / rotate secrets if you maintain sessions elsewhere

        return ResponseEntity.ok("Password changed");
    }

    public static class ChangePasswordRequest {
        private String currentPassword;
        private String newPassword;
        public String getCurrentPassword() { return currentPassword; }
        public void setCurrentPassword(String currentPassword) { this.currentPassword = currentPassword; }
        public String getNewPassword() { return newPassword; }
        public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
    }
}