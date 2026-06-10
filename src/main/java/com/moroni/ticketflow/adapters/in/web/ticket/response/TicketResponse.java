package com.moroni.ticketflow.adapters.in.web.ticket.response;

import com.moroni.ticketflow.application.core.domain.Ticket;
import com.moroni.ticketflow.application.core.domain.TicketDetails;
import com.moroni.ticketflow.application.core.domain.TicketPriority;
import com.moroni.ticketflow.application.core.domain.TicketStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class TicketResponse {

    private UUID id;
    private String title;
    private String description;
    private TicketStatus status;
    private TicketPriority priority;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime closedAt;

    private UUID createdByUserId;
    private String createdByUserName;

    private UUID assignedToUserId;
    private String assignedToUserName;

    private UUID categoryId;
    private String categoryName;

    private UUID supportQueueId;
    private String supportQueueName;

    public TicketResponse(
            UUID id,
            String title,
            String description,
            TicketStatus status,
            TicketPriority priority,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            LocalDateTime closedAt,
            UUID createdByUserId,
            String createdByUserName,
            UUID assignedToUserId,
            String assignedToUserName,
            UUID categoryId,
            String categoryName,
            UUID supportQueueId,
            String supportQueueName
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.closedAt = closedAt;
        this.createdByUserId = createdByUserId;
        this.createdByUserName = createdByUserName;
        this.assignedToUserId = assignedToUserId;
        this.assignedToUserName = assignedToUserName;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.supportQueueId = supportQueueId;
        this.supportQueueName = supportQueueName;
    }

    public static TicketResponse fromDomain(TicketDetails ticketDetails) {
        Ticket ticket = ticketDetails.getTicket();

        return new TicketResponse(
                ticket.getId(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getStatus(),
                ticket.getPriority(),
                ticket.getCreatedAt(),
                ticket.getUpdatedAt(),
                ticket.getClosedAt(),
                ticket.getCreatedByUserId(),
                ticketDetails.getCreatedByUserName(),
                ticket.getAssignedToUserId(),
                ticketDetails.getAssignedToUserName(),
                ticket.getCategoryId(),
                ticketDetails.getCategoryName(),
                ticket.getSupportQueueId(),
                ticketDetails.getSupportQueueName()
        );
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public TicketPriority getPriority() {
        return priority;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getClosedAt() {
        return closedAt;
    }

    public UUID getCreatedByUserId() {
        return createdByUserId;
    }

    public String getCreatedByUserName() {
        return createdByUserName;
    }

    public UUID getAssignedToUserId() {
        return assignedToUserId;
    }

    public String getAssignedToUserName() {
        return assignedToUserName;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public UUID getSupportQueueId() {
        return supportQueueId;
    }

    public String getSupportQueueName() {
        return supportQueueName;
    }
}