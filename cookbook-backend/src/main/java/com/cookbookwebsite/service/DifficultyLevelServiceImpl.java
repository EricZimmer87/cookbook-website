package com.cookbookwebsite.service;

import com.cookbookwebsite.dto.difficultylevel.DifficultyLevelDTO;
import com.cookbookwebsite.model.DifficultyLevel;
import com.cookbookwebsite.repository.DifficultyLevelRepository;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DifficultyLevelServiceImpl implements DifficultyLevelService {
    private final DifficultyLevelRepository difficultyLevelRepository;

    public DifficultyLevelServiceImpl(DifficultyLevelRepository difficultyLevelRepository) {
        this.difficultyLevelRepository = difficultyLevelRepository;
    }

    // Get all difficulty levels
    @Override
    @Transactional(readOnly = true)
    public List<DifficultyLevelDTO> getAllDifficultyLevelDTOs() {
        return difficultyLevelRepository.findAll()
                .stream()
                .map(DifficultyLevelDTO::new)
                .toList();
    }

    // Get difficultyLevelDTO by difficultyID
    @Override
    @Transactional(readOnly = true)
    public DifficultyLevelDTO getDifficultyLevelById(Integer difficultyLevelId) {
        DifficultyLevel difficultyLevel = difficultyLevelRepository.findById(difficultyLevelId)
                .orElseThrow(() -> new RuntimeException("Difficulty level not found with ID: " + difficultyLevelId));
        return new DifficultyLevelDTO(difficultyLevel);
    }

    // Get difficulty entity by difficulty ID
    @Override
    @Transactional(readOnly = true)
    public DifficultyLevel getDifficultyLevelEntityById(Integer id) {
        return difficultyLevelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Difficulty level not found."));
    }

    // Update difficulty level
    @Override
    @Transactional
    public DifficultyLevel saveDifficultyLevel(DifficultyLevel difficultyLevel) {
        DifficultyLevel saved = difficultyLevelRepository.save(difficultyLevel);
        Hibernate.initialize(saved.getRecipes());
        return saved;
    }

    // Create difficulty level
    @Override
    @Transactional
    public DifficultyLevel createDifficultyLevel(DifficultyLevel difficultyLevel) {
        return difficultyLevelRepository.save(difficultyLevel);
    }

    // Delete difficultyLevel
    @Override
    @Transactional
    public void deleteDifficultyLevelById(Integer difficultyId) {
        difficultyLevelRepository.deleteById(difficultyId);
    }
}
