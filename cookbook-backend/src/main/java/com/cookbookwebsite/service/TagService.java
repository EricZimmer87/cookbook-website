package com.cookbookwebsite.service;

import com.cookbookwebsite.dto.tag.TagDTO;
import com.cookbookwebsite.model.Tag;

import java.util.List;

public interface TagService {
    List<TagDTO> getAllTagDTOs();
    Tag createTag(Tag tag);
    TagDTO getTagById(Integer tagId);
    Tag getTagEntityById(Integer id);
    Tag saveTag(Tag tag);
    void deleteTagById(Integer tagId);
}
