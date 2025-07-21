package com.cookbookwebsite.repository;

import com.cookbookwebsite.model.DifficultyLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DifficultyLevelRepository extends JpaRepository<DifficultyLevel, Integer> {
}
