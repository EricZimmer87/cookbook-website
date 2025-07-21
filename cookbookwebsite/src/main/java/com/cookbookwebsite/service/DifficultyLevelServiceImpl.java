package com.cookbookwebsite.service;

import com.cookbookwebsite.dto.difficultylevel.DifficultyLevelDTO;
import com.cookbookwebsite.model.DifficultyLevel;
import com.cookbookwebsite.repository.DifficultyLevelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DifficultyLevelServiceImpl implements DifficultyLevelService {
    private final DifficultyLevelRepository difficultyLevelRepository;

    public DifficultyLevelServiceImpl(DifficultyLevelRepository difficultyLevelRepository) {
        this.difficultyLevelRepository = difficultyLevelRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DifficultyLevelDTO> getAllDifficultyLevelDTOs() {
        return difficultyLevelRepository.findAll()
                .stream()
                .map(DifficultyLevelDTO::new)
                .toList();
    }

    @Override
    public DifficultyLevel createDifficultyLevel(DifficultyLevel difficultyLevel) {
        return difficultyLevelRepository.save(difficultyLevel);
    }
}
