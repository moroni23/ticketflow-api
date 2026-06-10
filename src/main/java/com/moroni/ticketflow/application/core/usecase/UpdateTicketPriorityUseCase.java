package com.moroni.ticketflow.application.core.usecase;

import com.moroni.ticketflow.application.core.domain.Ticket;
import com.moroni.ticketflow.application.core.domain.TicketHistory;
import com.moroni.ticketflow.application.core.domain.TicketHistoryEventType;
import com.moroni.ticketflow.application.core.domain.TicketPriority;
import com.moroni.ticketflow.application.core.domain.UserRole;
import com.moroni.ticketflow.application.core.exception.InvalidTicketPriorityException;
import com.moroni.ticketflow.application.core.exception.TicketPriorityAlreadySetException;
import com.moroni.ticketflow.application.ports.in.UpdateTicketPriorityInputPort;
import com.moroni.ticketflow.application.ports.out.TicketHistoryRepositoryOutputPort;
import com.moroni.ticketflow.application.ports.out.TicketRepositoryOutputPort;

import java.util.UUID;

public class UpdateTicketPriorityUseCase implements UpdateTicketPriorityInputPort {

    private final TicketRepositoryOutputPort ticketRepositoryOutputPort;
    private final TicketHistoryRepositoryOutputPort ticketHistoryRepositoryOutputPort;
    private final ValidateTicketTechnicianAccessUseCase validateTicketTechnicianAccessUseCase;

    public UpdateTicketPriorityUseCase(
            TicketRepositoryOutputPort ticketRepositoryOutputPort,
            TicketHistoryRepositoryOutputPort ticketHistoryRepositoryOutputPort,
            ValidateTicketTechnicianAccessUseCase validateTicketTechnicianAccessUseCase
    ) {
        this.ticketRepositoryOutputPort = ticketRepositoryOutputPort;
        this.ticketHistoryRepositoryOutputPort = ticketHistoryRepositoryOutputPort;
        this.validateTicketTechnicianAccessUseCase = validateTicketTechnicianAccessUseCase;
    }

    @Override
    public Ticket updatePriority(
            UUID ticketId,
            String priority,
            UUID authenticatedUserId,
            UserRole authenticatedUserRole
    ) {
        Ticket ticket = validateTicketTechnicianAccessUseCase.validateAccess(
                ticketId,
                authenticatedUserId,
                authenticatedUserRole
        );

        TicketPriority oldPriority = ticket.getPriority();

        TicketPriority newPriority;

        try {
            newPriority = TicketPriority.valueOf(priority.trim().toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new InvalidTicketPriorityException(priority);
        }

        if (oldPriority == newPriority) {
            throw new TicketPriorityAlreadySetException(newPriority);
        }

        ticket.changePriority(newPriority);

        Ticket savedTicket = ticketRepositoryOutputPort.save(ticket);

        TicketHistory history = TicketHistory.create(
                savedTicket.getId(),
                TicketHistoryEventType.PRIORITY_CHANGED,
                "Priority changed from " + oldPriority + " to " + newPriority
        );

        ticketHistoryRepositoryOutputPort.save(history);

        return savedTicket;
    }
}