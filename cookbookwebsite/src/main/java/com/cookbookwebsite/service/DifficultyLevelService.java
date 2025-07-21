package com.cookbookwebsite.service;

import com.cookbookwebsite.dto.difficultylevel.DifficultyLevelDTO;
import com.cookbookwebsite.model.DifficultyLevel;
import java.util.List;

public interface DifficultyLevelService {
    List<DifficultyLevelDTO> getAllDifficultyLevelDTOs();
    DifficultyLevel createDifficultyLevel(DifficultyLevel difficultyLevel);
}
