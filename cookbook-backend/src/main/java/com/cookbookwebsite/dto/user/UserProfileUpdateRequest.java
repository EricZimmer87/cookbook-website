package com.cookbookwebsite.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserProfileUpdateRequest(
        @Size(min = 1, max = 255) String userName,
        @Email String userEmail
) {}