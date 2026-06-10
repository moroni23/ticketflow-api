package com.moroni.ticketflow.application.core.exception;

import com.moroni.ticketflow.application.core.domain.TicketStatus;

public class TicketStatusAlreadySetException extends RuntimeException {

    public TicketStatusAlreadySetException(TicketStatus status) {
        super("Ticket status is already set to: " + status);
    }
}