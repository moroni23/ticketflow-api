package com.moroni.ticketflow.application.core.usecase;

import com.moroni.ticketflow.application.core.domain.Ticket;
import com.moroni.ticketflow.application.core.domain.UserRole;
import com.moroni.ticketflow.application.core.exception.TicketAccessDeniedException;
import com.moroni.ticketflow.application.core.exception.TicketNotFoundException;
import com.moroni.ticketflow.application.ports.out.TicketRepositoryOutputPort;

import java.util.Objects;
import java.util.UUID;

public class ValidateTicketAccessUseCase {

    private final TicketRepositoryOutputPort ticketRepositoryOutputPort;

    public ValidateTicketAccessUseCase(
            TicketRepositoryOutputPort ticketRepositoryOutputPort
    ) {
        this.ticketRepositoryOutputPort = ticketRepositoryOutputPort;
    }

    public Ticket validate(
            UUID ticketId,
            UUID authenticatedUserId,
            UserRole authenticatedUserRole
    ) {
        Ticket ticket = ticketRepositoryOutputPort.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException(ticketId));

        return validate(
                ticket,
                authenticatedUserId,
                authenticatedUserRole
        );
    }

    public Ticket validateAccess(
            UUID ticketId,
            UUID authenticatedUserId,
            UserRole authenticatedUserRole
    ) {
        return validate(
                ticketId,
                authenticatedUserId,
                authenticatedUserRole
        );
    }

    public Ticket validate(
            Ticket ticket,
            UUID authenticatedUserId,
            UserRole authenticatedUserRole
    ) {
        if (UserRole.ADMIN.equals(authenticatedUserRole)) {
            return ticket;
        }

        if (UserRole.TECHNICIAN.equals(authenticatedUserRole)) {
            return ticket;
        }

        if (UserRole.USER.equals(authenticatedUserRole)
                && Objects.equals(ticket.getCreatedByUserId(), authenticatedUserId)) {
            return ticket;
        }

        throw new TicketAccessDeniedException(ticket.getId());
    }
}