package com.moroni.ticketflow.application.core.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class TicketComment {

    private UUID id;
    private UUID ticketId;
    private UUID authorUserId;
    private String message;
    private LocalDateTime createdAt;

    public TicketComment() {
    }

    public TicketComment(
            UUID id,
            UUID ticketId,
            UUID authorUserId,
            String message,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.ticketId = ticketId;
        this.authorUserId = authorUserId;
        this.message = message;
        this.createdAt = createdAt;
    }

    public static TicketComment create(
            UUID ticketId,
            UUID authorUserId,
            String message
    ) {
        return new TicketComment(
                UUID.randomUUID(),
                ticketId,
                authorUserId,
                message,
                LocalDateTime.now()
        );
    }

    public UUID getId() {
        return id;
    }

    public UUID getTicketId() {
        return ticketId;
    }

    public UUID getAuthorUserId() {
        return authorUserId;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}