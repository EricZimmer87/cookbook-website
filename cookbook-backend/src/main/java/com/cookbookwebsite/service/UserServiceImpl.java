package com.cookbookwebsite.service;

import com.cookbookwebsite.dto.user.UserDTO;
import com.cookbookwebsite.model.User;
import com.cookbookwebsite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Get all users
    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getAllUserDTOs() {
        return userRepository.findAll()
                .stream()
                .map(UserDTO::new)
                .toList();
    }

    // Get user by user ID
    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new UserDTO(user);
    }

    // Get user entity by user ID
    @Override
    @Transactional(readOnly = true)
    public User getUserEntityById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Find user by email
    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByUserEmail(email);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void updatePassword(User user, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPasswordHash(encodedPassword);
        userRepository.save(user);
    }

    // Create user
    @Override
    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Save user
    @Override
    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // Delete user
    @Override
    @Transactional
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
