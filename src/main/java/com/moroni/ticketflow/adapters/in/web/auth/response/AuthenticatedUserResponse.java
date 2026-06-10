package com.moroni.ticketflow.adapters.in.web.auth.response;

import com.moroni.ticketflow.application.core.domain.User;
import com.moroni.ticketflow.application.core.domain.UserRole;

import java.util.UUID;

public class AuthenticatedUserResponse {

    private UUID id;
    private String name;
    private String email;
    private UserRole role;

    public AuthenticatedUserResponse(UUID id, String name, String email, UserRole role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public static AuthenticatedUserResponse fromDomain(User user) {
        return new AuthenticatedUserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public UserRole getRole() {
        return role;
    }
}