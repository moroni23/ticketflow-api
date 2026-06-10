package com.moroni.ticketflow.application.core.usecase;

import com.moroni.ticketflow.application.core.domain.Ticket;
import com.moroni.ticketflow.application.core.domain.UserRole;
import com.moroni.ticketflow.application.core.exception.TechnicianNotAssignedException;
import com.moroni.ticketflow.application.core.exception.TicketNotFoundException;
import com.moroni.ticketflow.application.ports.out.TicketRepositoryOutputPort;

import java.util.UUID;

public class ValidateTicketTechnicianAccessUseCase {

    private final TicketRepositoryOutputPort ticketRepositoryOutputPort;

    public ValidateTicketTechnicianAccessUseCase(TicketRepositoryOutputPort ticketRepositoryOutputPort) {
        this.ticketRepositoryOutputPort = ticketRepositoryOutputPort;
    }

    public Ticket validateAccess(
            UUID ticketId,
            UUID authenticatedUserId,
            UserRole authenticatedUserRole
    ) {
        Ticket ticket = ticketRepositoryOutputPort.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException(ticketId));

        if (authenticatedUserRole == UserRole.ADMIN) {
            return ticket;
        }

        if (authenticatedUserRole == UserRole.TECHNICIAN
                && authenticatedUserId.equals(ticket.getAssignedToUserId())) {
            return ticket;
        }

        throw new TechnicianNotAssignedException(ticketId);
    }
}