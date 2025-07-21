package com.cookbookwebsite.dto.difficultylevel;

import com.cookbookwebsite.model.DifficultyLevel;

public class DifficultyLevelDTO {
    private Integer difficultyId;
    private String difficultyLevel;

    public DifficultyLevelDTO(DifficultyLevel difficultyLevel) {
        this.difficultyId = difficultyLevel.getDifficultyId();
        this.difficultyLevel = difficultyLevel.getDifficultyLevel();
    }

    // Getters
    public Integer getDifficultyId() { return difficultyId; }
    public String getDifficultyLevel() { return difficultyLevel; }
}
