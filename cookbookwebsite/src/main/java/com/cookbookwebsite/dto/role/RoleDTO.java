package com.cookbookwebsite.dto.role;

import com.cookbookwebsite.model.Role;

public class RoleDTO {
    private Integer roleId;
    private String roleName;

    public RoleDTO(Role role) {
        this.roleId = role.getRoleId();
        this.roleName = role.getRoleName();
    }

    public Integer getRoleId() {
        return roleId;
    }

    public String getRolename() {
        return roleName;
    }
}
