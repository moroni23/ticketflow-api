package com.moroni.ticketflow.adapters.in.web.supportqueue.response;

import com.moroni.ticketflow.application.core.domain.SupportQueue;

import java.time.LocalDateTime;
import java.util.UUID;

public class SupportQueueResponse {

    private UUID id;
    private String name;
    private String description;
    private boolean active;
    private LocalDateTime createdAt;

    public SupportQueueResponse(
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

    public static SupportQueueResponse fromDomain(SupportQueue supportQueue) {
        return new SupportQueueResponse(
                supportQueue.getId(),
                supportQueue.getName(),
                supportQueue.getDescription(),
                supportQueue.isActive(),
                supportQueue.getCreatedAt()
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