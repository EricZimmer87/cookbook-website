package com.cookbookwebsite.service;

import com.cookbookwebsite.dto.tag.TagDTO;
import com.cookbookwebsite.model.Tag;

import java.util.List;

public interface TagService {
    List<TagDTO> getAllTagDTOs();
    Tag createTag(Tag tag);
}
