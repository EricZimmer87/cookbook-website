package com.cookbookwebsite.service;

import com.cookbookwebsite.dto.userfavorite.UserFavoriteDTO;
import com.cookbookwebsite.model.UserFavorite;

import java.util.List;

public interface UserFavoriteService {
    List<UserFavoriteDTO> getFavoritesByUserId(Integer userId);
    List<UserFavoriteDTO> getFavoritesByRecipeId(Integer recipeId);
    UserFavorite createUserFavorite(UserFavorite userFavorite);
}
