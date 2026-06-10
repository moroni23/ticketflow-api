package com.moroni.ticketflow.application.ports.in;

import com.moroni.ticketflow.application.core.domain.Ticket;
import com.moroni.ticketflow.application.core.domain.UserRole;

import java.util.UUID;

public interface UpdateTicketPriorityInputPort {

    Ticket updatePriority(
            UUID ticketId,
            String priority,
            UUID authenticatedUserId,
            UserRole authenticatedUserRole
    );
}