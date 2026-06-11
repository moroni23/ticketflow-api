package com.moroni.ticketflow.adapters.in.web.ticket.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class UpdateTicketSupportQueueRequest {

    @NotNull(message = "Support queue id is required")
    private UUID supportQueueId;

    public UUID getSupportQueueId() {
        return supportQueueId;
    }
}