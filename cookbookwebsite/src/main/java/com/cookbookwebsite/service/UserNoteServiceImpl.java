package com.cookbookwebsite.service;

import com.cookbookwebsite.dto.usernote.UserNoteDTO;
import com.cookbookwebsite.model.UserNote;
import com.cookbookwebsite.repository.UserNoteRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserNoteServiceImpl implements UserNoteService {

    private final UserNoteRepository userNoteRepository;

    public UserNoteServiceImpl(UserNoteRepository userNoteRepository) {
        this.userNoteRepository = userNoteRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserNoteDTO> getNotesByUserId(Integer userId) {
        return userNoteRepository
            .findByUserUserId(userId)
            .stream()
            .map(UserNoteDTO::new)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserNoteDTO> getNotesByRecipeId(Integer recipeId) {
        return userNoteRepository
            .findByRecipeRecipeId(recipeId)
            .stream()
            .map(UserNoteDTO::new)
            .toList();
    }

    @Override
    public UserNote createUserNote(UserNote userNote) {
        return userNoteRepository.save(userNote);
    }
}
