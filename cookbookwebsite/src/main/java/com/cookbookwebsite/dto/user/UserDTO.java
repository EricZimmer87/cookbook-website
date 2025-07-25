package com.cookbookwebsite.dto.user;

import com.cookbookwebsite.model.Role;
import com.cookbookwebsite.model.User;

public class UserDTO {
    private Integer userId;
    private String userName;
    private String userEmail;
    private Role role;

    public UserDTO(User user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.userEmail = user.getUserEmail();
        this.role = user.getRole();
    }

    // Getters
    public Integer getUserId() { return userId; }
    public String getUserName() { return userName; }
    public String getUserEmail() { return userEmail; }
    public Role getRole() { return role; }
}
