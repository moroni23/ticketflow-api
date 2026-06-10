package com.moroni.ticketflow.application.core.domain;

public class TicketDetails {

    private Ticket ticket;
    private String createdByUserName;
    private String assignedToUserName;
    private String categoryName;
    private String supportQueueName;

    public TicketDetails(
            Ticket ticket,
            String createdByUserName,
            String assignedToUserName,
            String categoryName,
            String supportQueueName
    ) {
        this.ticket = ticket;
        this.createdByUserName = createdByUserName;
        this.assignedToUserName = assignedToUserName;
        this.categoryName = categoryName;
        this.supportQueueName = supportQueueName;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public String getCreatedByUserName() {
        return createdByUserName;
    }

    public String getAssignedToUserName() {
        return assignedToUserName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getSupportQueueName() {
        return supportQueueName;
    }
}