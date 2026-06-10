package com.moroni.ticketflow.application.core.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class TicketHistory {

    private UUID id;
    private UUID ticketId;
    private TicketHistoryEventType eventType;
    private String description;
    private LocalDateTime createdAt;

    public TicketHistory() {
    }

    public TicketHistory(
            UUID id,
            UUID ticketId,
            TicketHistoryEventType eventType,
            String description,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.ticketId = ticketId;
        this.eventType = eventType;
        this.description = description;
        this.createdAt = createdAt;
    }

    public static TicketHistory create(
            UUID ticketId,
            TicketHistoryEventType eventType,
            String description
    ) {
        return new TicketHistory(
                UUID.randomUUID(),
                ticketId,
                eventType,
                description,
                LocalDateTime.now()
        );
    }

    public UUID getId() {
        return id;
    }

    public UUID getTicketId() {
        return ticketId;
    }

    public TicketHistoryEventType getEventType() {
        return eventType;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}