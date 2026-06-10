package com.moroni.ticketflow.adapters.in.web.ticket.request;

import jakarta.validation.constraints.NotBlank;

public class UpdateTicketStatusRequest {

    @NotBlank(message = "Status is required")
    private String status;

    public String getStatus() {
        return status;
    }
}