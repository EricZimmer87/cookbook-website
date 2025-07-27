package com.cookbookwebsite.controller;

import com.cookbookwebsite.dto.role.RoleDTO;
import com.cookbookwebsite.model.Role;
import com.cookbookwebsite.service.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public List<RoleDTO> getAllRoleDTOs() {
        return roleService.getAllRoleDTOs();
    }

    @PostMapping
    public Role createRole(@RequestBody Role role) {
        return roleService.createRole(role);
    }
}
