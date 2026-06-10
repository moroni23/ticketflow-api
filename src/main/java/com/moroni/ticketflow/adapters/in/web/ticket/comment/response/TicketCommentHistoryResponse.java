package com.moroni.ticketflow.adapters.in.web.ticket.comment.response;

import com.moroni.ticketflow.application.core.domain.TicketComment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class TicketCommentHistoryResponse {

    private UUID id;
    private UUID ticketId;
    private UUID authorUserId;
    private String message;
    private LocalDateTime createdAt;
    private String date;
    private String time;
    private String dayOfWeek;

    public TicketCommentHistoryResponse(
            UUID id,
            UUID ticketId,
            UUID authorUserId,
            String message,
            LocalDateTime createdAt,
            String date,
            String time,
            String dayOfWeek
    ) {
        this.id = id;
        this.ticketId = ticketId;
        this.authorUserId = authorUserId;
        this.message = message;
        this.createdAt = createdAt;
        this.date = date;
        this.time = time;
        this.dayOfWeek = dayOfWeek;
    }

    public static TicketCommentHistoryResponse fromDomain(TicketComment ticketComment) {
        LocalDateTime createdAt = ticketComment.getCreatedAt();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        return new TicketCommentHistoryResponse(
                ticketComment.getId(),
                ticketComment.getTicketId(),
                ticketComment.getAuthorUserId(),
                ticketComment.getMessage(),
                createdAt,
                createdAt.format(dateFormatter),
                createdAt.format(timeFormatter),
                createdAt.getDayOfWeek().toString()
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

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }
}