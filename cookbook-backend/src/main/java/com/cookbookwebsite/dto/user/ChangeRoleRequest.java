package com.cookbookwebsite.dto.user;

import jakarta.validation.constraints.NotNull;

public record ChangeRoleRequest(
        @NotNull Integer roleId
) {}