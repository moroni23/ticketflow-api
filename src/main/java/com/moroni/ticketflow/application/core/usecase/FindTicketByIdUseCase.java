package com.moroni.ticketflow.application.core.usecase;

import com.moroni.ticketflow.application.core.domain.Ticket;
import com.moroni.ticketflow.application.core.domain.UserRole;
import com.moroni.ticketflow.application.core.exception.TicketAccessDeniedException;
import com.moroni.ticketflow.application.core.exception.TicketNotFoundException;
import com.moroni.ticketflow.application.ports.in.FindTicketByIdInputPort;
import com.moroni.ticketflow.application.ports.out.TicketRepositoryOutputPort;

import java.util.UUID;

public class FindTicketByIdUseCase implements FindTicketByIdInputPort {

    private final TicketRepositoryOutputPort ticketRepositoryOutputPort;

    public FindTicketByIdUseCase(TicketRepositoryOutputPort ticketRepositoryOutputPort) {
        this.ticketRepositoryOutputPort = ticketRepositoryOutputPort;
    }

    @Override
    public Ticket findById(
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