package com.moroni.ticketflow.application.core.exception;

import java.util.UUID;

public class InvalidTechnicianException extends RuntimeException {

    public InvalidTechnicianException(UUID userId) {
        super("User is not allowed to be assigned as technician. User id: " + userId);
    }
}