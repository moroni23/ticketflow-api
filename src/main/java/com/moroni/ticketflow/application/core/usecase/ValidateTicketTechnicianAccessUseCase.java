package com.moroni.ticketflow.application.core.usecase;

import com.moroni.ticketflow.application.core.domain.Ticket;
import com.moroni.ticketflow.application.core.domain.UserRole;
import com.moroni.ticketflow.application.core.exception.TechnicianNotAssignedException;
import com.moroni.ticketflow.application.core.exception.TicketNotFoundException;
import com.moroni.ticketflow.application.ports.out.TicketRepositoryOutputPort;

import java.util.Objects;
import java.util.UUID;

public class ValidateTicketTechnicianAccessUseCase {

    private final TicketRepositoryOutputPort ticketRepositoryOutputPort;

    public ValidateTicketTechnicianAccessUseCase(
            TicketRepositoryOutputPort ticketRepositoryOutputPort
    ) {
        this.ticketRepositoryOutputPort = ticketRepositoryOutputPort;
    }

    public Ticket validateAccess(
            UUID ticketId,
            UUID authenticatedUserId,
            UserRole authenticatedUserRole
    ) {
        Ticket ticket = ticketRepositoryOutputPort.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException(ticketId));

        validate(
                ticket,
                authenticatedUserId,
                authenticatedUserRole
        );

        return ticket;
    }

    public void validate(
            Ticket ticket,
            UUID authenticatedUserId,
            UserRole authenticatedUserRole
    ) {
        if (UserRole.ADMIN.equals(authenticatedUserRole)) {
            return;
        }

        if (UserRole.TECHNICIAN.equals(authenticatedUserRole)
                && Objects.equals(ticket.getAssignedToUserId(), authenticatedUserId)) {
            return;
        }

        throw new TechnicianNotAssignedException(ticket.getId());
    }
}