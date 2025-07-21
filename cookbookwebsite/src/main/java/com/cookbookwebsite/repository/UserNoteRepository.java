package com.cookbookwebsite.repository;

import com.cookbookwebsite.model.UserNote;
import com.cookbookwebsite.model.UserNoteId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserNoteRepository extends JpaRepository<UserNote, UserNoteId> {
    List<UserNote> findByUserUserId(Integer userId);
    List<UserNote> findByRecipeRecipeId(Integer recipeId);
}
