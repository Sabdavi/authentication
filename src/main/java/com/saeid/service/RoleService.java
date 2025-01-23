package com.saeid.service;

import com.saeid.Entity.Role;
import com.saeid.model.RoleDto;
import com.saeid.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RoleService {
    RoleRepository roleRepository;


    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    public RoleDto createRole(String name) {
        if(roleRepository.existsByName(name)) {
            throw new IllegalArgumentException("Role already exists");
        }
        Role role = new Role();
        role.setName(name);
        roleRepository.save(role);
        return toDto(role);
    }

    public Role findByName(String name) {
        return roleRepository.findByName(name).orElseThrow(() -> new IllegalArgumentException("Role not found"));
    }

    public Role getDefaluRole() {
        return findByName("ROLE_CUSTOMER");
    }

    RoleDto toDto(Role role) {
        return new RoleDto(role.getName());
    }
}
