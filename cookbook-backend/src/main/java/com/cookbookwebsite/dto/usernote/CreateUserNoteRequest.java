package com.cookbookwebsite.dto.usernote;

public class CreateUserNoteRequest {
    private String noteText;

    public CreateUserNoteRequest() {}

    public CreateUserNoteRequest(String noteText) {
        this.noteText = noteText;
    }
    public String getNoteText() { return noteText; }
    public void setNoteText(String noteText) { this.noteText = noteText; }
}