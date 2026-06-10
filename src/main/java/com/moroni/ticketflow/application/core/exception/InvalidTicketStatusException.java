package com.moroni.ticketflow.application.core.exception;

public class InvalidTicketStatusException extends RuntimeException {

    public InvalidTicketStatusException(String status) {
        super("Invalid ticket status: " + status + ". Allowed values: OPEN, IN_PROGRESS, WAITING_USER, RESOLVED, CLOSED, CANCELED");
    }
}