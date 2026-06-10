package com.moroni.ticketflow.adapters.in.web.ticket.comment.response;

import com.moroni.ticketflow.application.core.domain.TicketComment;

import java.time.LocalDateTime;
import java.util.UUID;

public class TicketCommentResponse {

    private UUID id;
    private UUID ticketId;
    private UUID authorUserId;
    private String message;
    private LocalDateTime createdAt;

    public TicketCommentResponse(
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

    public static TicketCommentResponse fromDomain(TicketComment ticketComment) {
        return new TicketCommentResponse(
                ticketComment.getId(),
                ticketComment.getTicketId(),
                ticketComment.getAuthorUserId(),
                ticketComment.getMessage(),
                ticketComment.getCreatedAt()
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