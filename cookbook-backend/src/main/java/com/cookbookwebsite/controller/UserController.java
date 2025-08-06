package com.cookbookwebsite.controller;

import com.cookbookwebsite.dto.user.UserDTO;
import com.cookbookwebsite.model.Role;
import com.cookbookwebsite.model.User;
import com.cookbookwebsite.repository.RoleRepository;
import com.cookbookwebsite.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    @PreAuthorize("#id == principal.userId or hasRole('ADMIN')")
    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable Integer id, @RequestBody User updatedUser) {
        User user = userService.getUserEntityById(id); // Return the full entity

        if (updatedUser.getUserName() != null) {
            user.setUserName(updatedUser.getUserName());
        }
        if (updatedUser.getUserEmail() != null) {
            user.setUserEmail(updatedUser.getUserEmail());
        }
        if (updatedUser.getRole() != null && updatedUser.getRole().getRoleId() != null) {
            Role role = roleRepository.findById(updatedUser.getRole().getRoleId())
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            user.setRole(role);
        }

        User savedUser = userService.saveUser(user);
        return new UserDTO(savedUser);
    }

    // Delete user (DELETE)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }
}
