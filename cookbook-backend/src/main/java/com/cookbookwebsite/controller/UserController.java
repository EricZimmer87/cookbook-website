package com.cookbookwebsite.controller;

import com.cookbookwebsite.dto.user.UserDTO;
import com.cookbookwebsite.model.User;
import com.cookbookwebsite.repository.RoleRepository;
import com.cookbookwebsite.repository.UserRepository;
import com.cookbookwebsite.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.cookbookwebsite.dto.user.UserProfileUpdateRequest;
import com.cookbookwebsite.dto.user.ChangeRoleRequest;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public UserController(UserService userService, RoleRepository roleRepository, UserRepository userRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    // Get all users
    @PreAuthorize("hasRole('ADMIN')") // Only allow admin access
    @GetMapping
    public List<UserDTO> getAllUserDTOs() {
        return userService.getAllUserDTOs();
    }

    // Get user by ID
    // Allow admin or user_id of currently logged in user
    @PreAuthorize("#id == principal.userId or hasRole('ADMIN')")
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    // Create user
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // Update user (PUT)
    // Only self or admin; cannot change role here
    @PreAuthorize("#id == principal.userId or hasRole('ADMIN')")
    @PutMapping("/{id}")
    public UserDTO updateUser(
            @PathVariable Integer id,
            @Valid @RequestBody UserProfileUpdateRequest req
    ) {
        var user = userService.getUserEntityById(id);
        if (req.userName() != null) user.setUserName(req.userName());
        if (req.userEmail() != null) user.setUserEmail(req.userEmail());
        return new UserDTO(userService.saveUser(user));
    }

    // Admin-only role change
    @PreAuthorize("hasRole('ADMIN') and #id != principal.userId")
    @PutMapping("/{id}/role")
    public UserDTO changeRole(
            @PathVariable Integer id,
            @Valid @RequestBody ChangeRoleRequest req
    ) {
        var user = userService.getUserEntityById(id);
        var role = roleRepository.findById(req.roleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);
        return new UserDTO(userService.saveUser(user));
    }

    @PreAuthorize("hasAnyRole('ADMIN','CONTRIBUTOR')")
    @GetMapping("/by-roles")
    public List<UserDTO> getUsersByRoles(@RequestParam List<String> roles) {
        // Normalize role names to match how they’re stored (e.g. ADMIN / CONTRIBUTOR)
        var normalized = roles.stream()
                .map(r -> r.trim().toUpperCase(Locale.ROOT))
                .collect(Collectors.toList());

        var users = userRepository.findByRole_RoleNameIn(normalized);
        return users.stream().map(UserDTO::new).toList();
    }

    // Delete user
    @PreAuthorize("hasRole('ADMIN') and #id != principal.userId")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Ban user
    @PreAuthorize("hasRole('ADMIN') and #id != principal.userId")
    @PutMapping("/{id}/ban")
    public ResponseEntity<Void> banUser(@PathVariable Integer id) {
        userService.banUser(id);
        return ResponseEntity.noContent().build();
    }

    // Unban user
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/unban")
    public ResponseEntity<Void> unbanUser(@PathVariable Integer id) {
        userService.unbanUser(id);
        return ResponseEntity.noContent().build();
    }
}
