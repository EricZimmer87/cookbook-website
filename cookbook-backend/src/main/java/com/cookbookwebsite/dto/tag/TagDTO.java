package com.cookbookwebsite.dto.tag;

import com.cookbookwebsite.model.Tag;

public class TagDTO {
    private Integer tagId;
    private String tagName;

    public TagDTO(Tag tag) {
        this.tagId = tag.getTagId();
        this.tagName = tag.getTagName();
    }

    // Getters
    public Integer getTagId() { return tagId; }
    public String getTagName() { return tagName; }
}
