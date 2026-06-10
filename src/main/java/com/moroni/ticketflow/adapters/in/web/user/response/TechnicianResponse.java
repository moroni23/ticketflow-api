package com.moroni.ticketflow.adapters.in.web.user.response;

import com.moroni.ticketflow.application.core.domain.User;
import com.moroni.ticketflow.application.core.domain.UserRole;

import java.util.UUID;

public class TechnicianResponse {

    private UUID id;
    private String name;
    private String email;
    private UserRole role;

    public TechnicianResponse(UUID id, String name, String email, UserRole role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public static TechnicianResponse fromDomain(User user) {
        return new TechnicianResponse(
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
