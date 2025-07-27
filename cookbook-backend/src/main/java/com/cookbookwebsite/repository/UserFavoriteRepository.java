package com.cookbookwebsite.repository;

import com.cookbookwebsite.model.UserFavorite;
import com.cookbookwebsite.model.UserFavoriteId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserFavoriteRepository extends JpaRepository<UserFavorite, UserFavoriteId> {
    List<UserFavorite> findByUserUserId(Integer userId);
    List<UserFavorite> findByRecipeRecipeId(Integer recipeId);
}