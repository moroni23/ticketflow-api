package com.moroni.ticketflow.application.core.exception;

import java.util.UUID;

public class TechnicianNotAssignedException extends RuntimeException {

    public TechnicianNotAssignedException(UUID ticketId) {
        super("Technician is not assigned to ticket with id: " + ticketId);
    }
}