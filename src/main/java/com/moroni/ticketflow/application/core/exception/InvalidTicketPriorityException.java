package com.moroni.ticketflow.application.core.exception;

public class InvalidTicketPriorityException extends RuntimeException {

    public InvalidTicketPriorityException(String priority) {
        super("Invalid ticket priority: " + priority + ". Allowed values: LOW, MEDIUM, HIGH, CRITICAL");
    }
}