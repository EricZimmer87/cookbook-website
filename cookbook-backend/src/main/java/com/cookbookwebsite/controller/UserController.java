package com.cookbookwebsite.controller;

import com.cookbookwebsite.dto.user.UserDTO;
import com.cookbookwebsite.model.User;
import com.cookbookwebsite.repository.RoleRepository;
import com.cookbookwebsite.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.cookbookwebsite.dto.user.UserProfileUpdateRequest;
import com.cookbookwebsite.dto.user.ChangeRoleRequest;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final RoleRepository roleRepository;

    public UserController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
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

    // Delete user (DELETE)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }
}
