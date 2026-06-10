package com.moroni.ticketflow.application.ports.in;

import com.moroni.ticketflow.application.core.domain.Ticket;
import com.moroni.ticketflow.application.core.domain.TicketPriority;

import java.util.UUID;

public interface CreateTicketInputPort {

    Ticket create(
            String title,
            String description,
            TicketPriority priority,
            UUID createdByUserId,
            UUID categoryId,
            UUID supportQueueId
    );
}