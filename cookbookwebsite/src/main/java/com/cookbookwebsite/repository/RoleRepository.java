package com.cookbookwebsite.repository;

import com.cookbookwebsite.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}