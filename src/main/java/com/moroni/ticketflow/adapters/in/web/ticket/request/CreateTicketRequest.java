package com.moroni.ticketflow.adapters.in.web.ticket.request;

import com.moroni.ticketflow.application.core.domain.TicketPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class CreateTicketRequest {

    @NotBlank(message = "Title is required")
    @Size(max = 150, message = "Title must have at most 150 characters")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Priority is required")
    private TicketPriority priority;

    @NotNull(message = "Category id is required")
    private UUID categoryId;

    @NotNull(message = "Support queue id is required")
    private UUID supportQueueId;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TicketPriority getPriority() {
        return priority;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public UUID getSupportQueueId() {
        return supportQueueId;
    }
}