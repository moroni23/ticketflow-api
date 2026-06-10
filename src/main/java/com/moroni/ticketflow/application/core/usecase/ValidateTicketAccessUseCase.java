package com.moroni.ticketflow.application.core.usecase;

import com.moroni.ticketflow.application.core.domain.Ticket;
import com.moroni.ticketflow.application.core.domain.UserRole;
import com.moroni.ticketflow.application.core.exception.TicketAccessDeniedException;
import com.moroni.ticketflow.application.core.exception.TicketNotFoundException;
import com.moroni.ticketflow.application.ports.out.TicketRepositoryOutputPort;

import java.util.UUID;

public class ValidateTicketAccessUseCase {

    private final TicketRepositoryOutputPort ticketRepositoryOutputPort;

    public ValidateTicketAccessUseCase(TicketRepositoryOutputPort ticketRepositoryOutputPort) {
        this.ticketRepositoryOutputPort = ticketRepositoryOutputPort;
    }

    public Ticket validateAccess(
            UUID ticketId,
            UUID authenticatedUserId,
            UserRole authenticatedUserRole
    ) {
        Ticket ticket = ticketRepositoryOutputPort.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException(ticketId));

        if (authenticatedUserRole == UserRole.ADMIN || authenticatedUserRole == UserRole.TECHNICIAN) {
            return ticket;
        }

        if (!ticket.getCreatedByUserId().equals(authenticatedUserId)) {
            throw new TicketAccessDeniedException(ticketId);
        }

        return ticket;
    }
}
