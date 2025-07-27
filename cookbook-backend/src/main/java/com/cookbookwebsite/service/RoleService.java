package com.cookbookwebsite.service;

import com.cookbookwebsite.dto.role.RoleDTO;
import com.cookbookwebsite.model.Role;
import java.util.List;

public interface RoleService {
    List<RoleDTO> getAllRoleDTOs();
    Role createRole(Role role);
}
