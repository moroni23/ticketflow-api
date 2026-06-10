package com.moroni.ticketflow.application.core.exception;

import java.util.UUID;

public class TicketAccessDeniedException extends RuntimeException {

    public TicketAccessDeniedException(UUID ticketId) {
        super("You are not allowed to access ticket with id: " + ticketId);
    }
}
