package com.moroni.ticketflow.application.core.exception;

import com.moroni.ticketflow.application.core.domain.TicketPriority;

public class TicketPriorityAlreadySetException extends RuntimeException {

    public TicketPriorityAlreadySetException(TicketPriority priority) {
        super("Ticket priority is already set to: " + priority);
    }
}