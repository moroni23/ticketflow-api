package com.moroni.ticketflow.adapters.in.web.ticket.comment.request;

import jakarta.validation.constraints.NotBlank;

public class AddTicketCommentRequest {

    @NotBlank(message = "Message is required")
    private String message;

    public String getMessage() {
        return message;
    }
}