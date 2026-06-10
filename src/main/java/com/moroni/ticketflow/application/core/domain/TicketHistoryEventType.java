package com.moroni.ticketflow.application.core.domain;

public enum TicketHistoryEventType {
    TICKET_CREATED,
    STATUS_CHANGED,
    PRIORITY_CHANGED,
    TECHNICIAN_ASSIGNED,
    COMMENT_ADDED,
    TICKET_CLOSED
}