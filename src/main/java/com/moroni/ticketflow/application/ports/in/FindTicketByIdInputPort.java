package com.moroni.ticketflow.application.ports.in;

import com.moroni.ticketflow.application.core.domain.Ticket;
import com.moroni.ticketflow.application.core.domain.UserRole;

import java.util.UUID;

public interface FindTicketByIdInputPort {

    Ticket findById(
            UUID ticketId,
            UUID authenticatedUserId,
            UserRole authenticatedUserRole
    );
}