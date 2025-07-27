package com.cookbookwebsite.dto.auth;

public class LoginRequest {
    private String userEmail;
    private String password;

    // Getters and setters
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}