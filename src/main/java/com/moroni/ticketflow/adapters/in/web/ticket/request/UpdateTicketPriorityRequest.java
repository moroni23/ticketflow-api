package com.moroni.ticketflow.adapters.in.web.ticket.request;

import jakarta.validation.constraints.NotBlank;

public class UpdateTicketPriorityRequest {

    @NotBlank(message = "Priority is required")
    private String priority;

    public String getPriority() {
        return priority;
    }
}