package com.cookbookwebsite.service;

import com.cookbookwebsite.dto.user.UserDTO;
import com.cookbookwebsite.model.User;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUserDTOs();
    User createUser(User user);
}
