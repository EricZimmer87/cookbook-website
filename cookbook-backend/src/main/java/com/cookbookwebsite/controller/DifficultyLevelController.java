package com.cookbookwebsite.controller;

import com.cookbookwebsite.dto.difficultylevel.DifficultyLevelDTO;
import com.cookbookwebsite.model.DifficultyLevel;
import com.cookbookwebsite.service.DifficultyLevelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/difficulty-levels")
public class DifficultyLevelController {
    private final DifficultyLevelService difficultyLevelService;

    public DifficultyLevelController(DifficultyLevelService difficultyLevelService) {
        this.difficultyLevelService = difficultyLevelService;
    }

    @GetMapping
    public List<DifficultyLevelDTO> getAllDifficultyLevelDTOs() {
        return difficultyLevelService.getAllDifficultyLevelDTOs();
    }

    @PostMapping
    public DifficultyLevel createDifficultyLevel(@RequestBody DifficultyLevel difficultyLevel) {
        return difficultyLevelService.createDifficultyLevel(difficultyLevel);
    }
}
