package com.moroni.ticketflow.application.core.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class SupportQueue {

    private UUID id;
    private String name;
    private String description;
    private boolean active;
    private LocalDateTime createdAt;

    public SupportQueue() {
    }

    public SupportQueue(
            UUID id,
            String name,
            String description,
            boolean active,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.active = active;
        this.createdAt = createdAt;
    }

    public static SupportQueue create(String name, String description) {
        return new SupportQueue(
                UUID.randomUUID(),
                name,
                description,
                true,
                LocalDateTime.now()
        );
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}