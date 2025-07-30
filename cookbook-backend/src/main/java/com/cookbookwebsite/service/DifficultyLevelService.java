package com.cookbookwebsite.service;

import com.cookbookwebsite.dto.category.CategoryDTO;
import com.cookbookwebsite.dto.difficultylevel.DifficultyLevelDTO;
import com.cookbookwebsite.model.Category;
import com.cookbookwebsite.model.DifficultyLevel;
import java.util.List;

public interface DifficultyLevelService {
    List<DifficultyLevelDTO> getAllDifficultyLevelDTOs();
    DifficultyLevel createDifficultyLevel(DifficultyLevel difficultyLevel);
    DifficultyLevelDTO getDifficultyLevelById(Integer difficultyLevelId);
    DifficultyLevel saveDifficultyLevel(DifficultyLevel difficultyLevel);
    DifficultyLevel getDifficultyLevelEntityById(Integer id);
    void deleteDifficultyLevelById(Integer difficultyLevelId);

}
