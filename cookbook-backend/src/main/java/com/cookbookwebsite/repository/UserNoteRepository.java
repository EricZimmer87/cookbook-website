package com.cookbookwebsite.repository;

import com.cookbookwebsite.model.UserNote;
import com.cookbookwebsite.model.UserNoteId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserNoteRepository extends JpaRepository<UserNote, UserNoteId> {
    List<UserNote> findByUserUserId(Integer userId);
    List<UserNote> findByRecipeRecipeId(Integer recipeId);
    Optional<UserNote> findByUser_UserIdAndRecipe_RecipeId(Integer userId, Integer recipeId);
    void deleteByUser_UserIdAndRecipe_RecipeId(Integer userId, Integer recipeId);
    void deleteByRecipe_RecipeId(Integer recipeId);
}
