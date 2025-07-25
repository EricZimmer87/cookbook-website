package com.cookbookwebsite.dto.auth;

import com.cookbookwebsite.dto.user.UserDTO;

public class LoginResponse {
    private UserDTO user;
    private String token;

    public LoginResponse(UserDTO user, String token) {
        this.user = user;
        this.token = token;
    }

    public UserDTO getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }
}