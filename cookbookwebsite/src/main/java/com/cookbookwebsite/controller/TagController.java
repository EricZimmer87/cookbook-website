package com.cookbookwebsite.controller;

import com.cookbookwebsite.dto.tag.TagDTO;
import com.cookbookwebsite.model.Tag;
import com.cookbookwebsite.service.TagService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<TagDTO> getAllTagDTOs() {
        return tagService.getAllTagDTOs();
    }

    @PostMapping
    public Tag createTag(@RequestBody Tag tag) {
        return tagService.createTag(tag);
    }
}
