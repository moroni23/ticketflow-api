package com.moroni.ticketflow.adapters.in.web.ticket.history.response;

import com.moroni.ticketflow.application.core.domain.TicketHistory;
import com.moroni.ticketflow.application.core.domain.TicketHistoryEventType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class TicketHistoryResponse {

    private UUID id;
    private UUID ticketId;
    private TicketHistoryEventType eventType;
    private String description;
    private LocalDateTime createdAt;
    private String date;
    private String time;
    private String dayOfWeek;

    public TicketHistoryResponse(
            UUID id,
            UUID ticketId,
            TicketHistoryEventType eventType,
            String description,
            LocalDateTime createdAt,
            String date,
            String time,
            String dayOfWeek
    ) {
        this.id = id;
        this.ticketId = ticketId;
        this.eventType = eventType;
        this.description = description;
        this.createdAt = createdAt;
        this.date = date;
        this.time = time;
        this.dayOfWeek = dayOfWeek;
    }

    public static TicketHistoryResponse fromDomain(TicketHistory ticketHistory) {
        LocalDateTime createdAt = ticketHistory.getCreatedAt();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        return new TicketHistoryResponse(
                ticketHistory.getId(),
                ticketHistory.getTicketId(),
                ticketHistory.getEventType(),
                ticketHistory.getDescription(),
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

    public TicketHistoryEventType getEventType() {
        return eventType;
    }

    public String getDescription() {
        return description;
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