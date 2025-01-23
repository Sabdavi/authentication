package com.saeid.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;


public class UserDto {
    @NotNull
    private String username;
    @NotNull
    private String password;
    private Set<RoleDto> roles = new HashSet<>();


    public UserDto(String username, String password) {
        this(username, password,Set.of(new RoleDto("CUSTOMER")));
        this.username = username;
        this.password = password;
    }


    @JsonCreator
    public UserDto(String username, String password, Set<RoleDto> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Set<RoleDto> getRoles() {
        return roles;
    }
}
