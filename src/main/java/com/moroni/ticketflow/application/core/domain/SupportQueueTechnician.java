package com.moroni.ticketflow.application.core.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class SupportQueueTechnician {

    private UUID id;
    private UUID supportQueueId;
    private UUID technicianId;
    private LocalDateTime createdAt;

    public SupportQueueTechnician() {
    }

    public SupportQueueTechnician(
            UUID id,
            UUID supportQueueId,
            UUID technicianId,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.supportQueueId = supportQueueId;
        this.technicianId = technicianId;
        this.createdAt = createdAt;
    }

    public static SupportQueueTechnician create(
            UUID supportQueueId,
            UUID technicianId
    ) {
        return new SupportQueueTechnician(
                UUID.randomUUID(),
                supportQueueId,
                technicianId,
                LocalDateTime.now()
        );
    }

    public UUID getId() {
        return id;
    }

    public UUID getSupportQueueId() {
        return supportQueueId;
    }

    public UUID getTechnicianId() {
        return technicianId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}