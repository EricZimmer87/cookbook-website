package com.cookbookwebsite.controller;

import com.cookbookwebsite.dto.auth.LoginRequest;
import com.cookbookwebsite.dto.auth.RegisterRequest;
import com.cookbookwebsite.model.Role;
import com.cookbookwebsite.model.User;
import com.cookbookwebsite.repository.RoleRepository;
import com.cookbookwebsite.repository.UserRepository;
import com.cookbookwebsite.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
    public String login(@RequestBody LoginRequest request) {
        User user = userRepository.findByUserEmail(request.getUserEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid email or password.");
        }

        String token = jwtService.generateToken(user.getUserEmail());
        return token;
    }
}