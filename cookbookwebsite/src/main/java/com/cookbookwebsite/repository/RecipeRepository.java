package com.cookbookwebsite.repository;

import com.cookbookwebsite.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    List<Recipe> findByUserUserId(Integer userId);
}
