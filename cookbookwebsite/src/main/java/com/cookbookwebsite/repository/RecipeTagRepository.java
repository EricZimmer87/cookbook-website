package com.cookbookwebsite.repository;

import com.cookbookwebsite.model.RecipeTag;
import com.cookbookwebsite.model.RecipeTagId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeTagRepository extends JpaRepository<RecipeTag, RecipeTagId> {
}
