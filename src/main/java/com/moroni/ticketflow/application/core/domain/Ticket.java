package com.moroni.ticketflow.application.core.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Ticket {

    private UUID id;
    private String title;
    private String description;
    private TicketStatus status;
    private TicketPriority priority;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime closedAt;
    private UUID createdByUserId;
    private UUID assignedToUserId;
    private UUID categoryId;
    private UUID supportQueueId;

    public Ticket() {
    }

    public Ticket(
            UUID id,
            String title,
            String description,
            TicketStatus status,
            TicketPriority priority,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            LocalDateTime closedAt,
            UUID createdByUserId,
            UUID assignedToUserId,
            UUID categoryId,
            UUID supportQueueId
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
        this.assignedToUserId = assignedToUserId;
        this.categoryId = categoryId;
        this.supportQueueId = supportQueueId;
    }

    public static Ticket create(
            String title,
            String description,
            TicketPriority priority,
            UUID createdByUserId,
            UUID categoryId,
            UUID supportQueueId
    ) {
        LocalDateTime now = LocalDateTime.now();

        return new Ticket(
                UUID.randomUUID(),
                title,
                description,
                TicketStatus.OPEN,
                priority,
                now,
                now,
                null,
                createdByUserId,
                null,
                categoryId,
                supportQueueId
        );
    }

    public void changeStatus(TicketStatus newStatus) {
        this.status = newStatus;
        this.updatedAt = LocalDateTime.now();

        if (TicketStatus.CLOSED.equals(newStatus)) {
            this.closedAt = LocalDateTime.now();
        }
    }

    public void changePriority(TicketPriority newPriority) {
        this.priority = newPriority;
        this.updatedAt = LocalDateTime.now();
    }

    public void assignTo(UUID technicianId) {
        this.assignedToUserId = technicianId;
        this.updatedAt = LocalDateTime.now();

        if (TicketStatus.OPEN.equals(this.status)) {
            this.status = TicketStatus.IN_PROGRESS;
        }
    }

    public void changeSupportQueue(UUID newSupportQueueId) {
        this.supportQueueId = newSupportQueueId;
        this.updatedAt = LocalDateTime.now();
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

    public UUID getAssignedToUserId() {
        return assignedToUserId;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public UUID getSupportQueueId() {
        return supportQueueId;
    }
}