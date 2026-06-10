package com.moroni.ticketflow.application.core.exception;

import java.util.UUID;

public class TicketNotFoundException extends RuntimeException {

    public TicketNotFoundException(UUID id) {
        super("Ticket not found with id: " + id);
    }
}