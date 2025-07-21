package com.cookbookwebsite.service;

import com.cookbookwebsite.dto.role.RoleDTO;
import com.cookbookwebsite.model.Role;
import com.cookbookwebsite.repository.RoleRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleDTO> getAllRoleDTOs() {
        return roleRepository.findAll()
                .stream()
                .map(RoleDTO::new)
                .toList();
    }

    @Override
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }
}
