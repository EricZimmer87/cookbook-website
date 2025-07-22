package com.cookbookwebsite.repository;

import com.cookbookwebsite.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

}
