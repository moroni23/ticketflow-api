package com.moroni.ticketflow.application.core.usecase;

import com.moroni.ticketflow.application.core.domain.Ticket;
import com.moroni.ticketflow.application.core.domain.TicketHistory;
import com.moroni.ticketflow.application.core.domain.TicketHistoryEventType;
import com.moroni.ticketflow.application.core.domain.TicketStatus;
import com.moroni.ticketflow.application.core.domain.UserRole;
import com.moroni.ticketflow.application.core.exception.InvalidTicketStatusException;
import com.moroni.ticketflow.application.core.exception.TicketStatusAlreadySetException;
import com.moroni.ticketflow.application.ports.in.UpdateTicketStatusInputPort;
import com.moroni.ticketflow.application.ports.out.TicketHistoryRepositoryOutputPort;
import com.moroni.ticketflow.application.ports.out.TicketRepositoryOutputPort;

import java.util.UUID;

public class UpdateTicketStatusUseCase implements UpdateTicketStatusInputPort {

    private final TicketRepositoryOutputPort ticketRepositoryOutputPort;
    private final TicketHistoryRepositoryOutputPort ticketHistoryRepositoryOutputPort;
    private final ValidateTicketTechnicianAccessUseCase validateTicketTechnicianAccessUseCase;

    public UpdateTicketStatusUseCase(
            TicketRepositoryOutputPort ticketRepositoryOutputPort,
            TicketHistoryRepositoryOutputPort ticketHistoryRepositoryOutputPort,
            ValidateTicketTechnicianAccessUseCase validateTicketTechnicianAccessUseCase
    ) {
        this.ticketRepositoryOutputPort = ticketRepositoryOutputPort;
        this.ticketHistoryRepositoryOutputPort = ticketHistoryRepositoryOutputPort;
        this.validateTicketTechnicianAccessUseCase = validateTicketTechnicianAccessUseCase;
    }

    @Override
    public Ticket updateStatus(
            UUID ticketId,
            String status,
            UUID authenticatedUserId,
            UserRole authenticatedUserRole
    ) {
        Ticket ticket = validateTicketTechnicianAccessUseCase.validateAccess(
                ticketId,
                authenticatedUserId,
                authenticatedUserRole
        );

        TicketStatus oldStatus = ticket.getStatus();

        TicketStatus newStatus;

        try {
            newStatus = TicketStatus.valueOf(status.trim().toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new InvalidTicketStatusException(status);
        }

        if (oldStatus == newStatus) {
            throw new TicketStatusAlreadySetException(newStatus);
        }

        ticket.changeStatus(newStatus);

        Ticket savedTicket = ticketRepositoryOutputPort.save(ticket);

        TicketHistory statusHistory = TicketHistory.create(
                savedTicket.getId(),
                TicketHistoryEventType.STATUS_CHANGED,
                "Status changed from " + oldStatus + " to " + newStatus
        );

        ticketHistoryRepositoryOutputPort.save(statusHistory);

        if (newStatus == TicketStatus.CLOSED) {
            TicketHistory closedHistory = TicketHistory.create(
                    savedTicket.getId(),
                    TicketHistoryEventType.TICKET_CLOSED,
                    "Ticket closed"
            );

            ticketHistoryRepositoryOutputPort.save(closedHistory);
        }

        return savedTicket;
    }
}