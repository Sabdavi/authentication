package com.saeid.controller;

import com.saeid.model.RoleDto;
import com.saeid.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {
    RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/role")
    public ResponseEntity<RoleDto> createRole(@RequestParam() String name) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.createRole(name));
    }
}
