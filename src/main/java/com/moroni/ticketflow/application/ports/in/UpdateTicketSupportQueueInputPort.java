package com.moroni.ticketflow.application.ports.in;

import com.moroni.ticketflow.application.core.domain.Ticket;
import com.moroni.ticketflow.application.core.domain.UserRole;

import java.util.UUID;

public interface UpdateTicketSupportQueueInputPort {

    Ticket updateSupportQueue(
            UUID ticketId,
            UUID supportQueueId,
            UUID authenticatedUserId,
            UserRole authenticatedUserRole
    );
}