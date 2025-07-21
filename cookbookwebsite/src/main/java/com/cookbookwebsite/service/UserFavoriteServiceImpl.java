package com.cookbookwebsite.service;

import com.cookbookwebsite.dto.userfavorite.UserFavoriteDTO;
import com.cookbookwebsite.model.UserFavorite;
import com.cookbookwebsite.repository.UserFavoriteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserFavoriteServiceImpl implements UserFavoriteService {
    private final UserFavoriteRepository userFavoriteRepository;

    public UserFavoriteServiceImpl(UserFavoriteRepository userFavoriteRepository)  {
        this.userFavoriteRepository = userFavoriteRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserFavoriteDTO> getFavoritesByUserId(Integer userId) {
        return userFavoriteRepository.findByUserUserId(userId)
                .stream()
                .map(UserFavoriteDTO::new)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserFavoriteDTO> getFavoritesByRecipeId(Integer recipeId) {
        return userFavoriteRepository.findByRecipeRecipeId(recipeId)
                .stream()
                .map(UserFavoriteDTO::new)
                .toList();
    }

    @Override
    public UserFavorite createUserFavorite(UserFavorite userFavorite) {
        return userFavoriteRepository.save(userFavorite);
    }
}
