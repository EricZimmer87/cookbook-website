package com.cookbookwebsite.repository;

import com.cookbookwebsite.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserEmail(String userEmail);
    List<User> findByRole_RoleNameIn(List<String> roleNames);
}
