package com.moroni.ticketflow.application.core.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Category {

    private UUID id;
    private String name;
    private String description;
    private LocalDateTime createdAt;

    public Category() {
    }

    public Category(UUID id, String name, String description, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
    }

    public static Category create(String name, String description) {
        return new Category(
                UUID.randomUUID(),
                name,
                description,
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}