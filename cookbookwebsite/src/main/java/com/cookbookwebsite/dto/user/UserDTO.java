package com.cookbookwebsite.dto.user;

import com.cookbookwebsite.model.User;

public class UserDTO {
    private Integer userId;
    private String userName;
    private String email;

    public UserDTO(User user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.email = user.getUserEmail();
    }

    // Getters
    public Integer getUserId() { return userId; }
    public String getUserName() { return userName; }
    public String getEmail() { return email; }
}
