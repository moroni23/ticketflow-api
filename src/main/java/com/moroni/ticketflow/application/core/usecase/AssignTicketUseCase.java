package com.moroni.ticketflow.application.core.usecase;

import com.moroni.ticketflow.application.core.domain.Ticket;
import com.moroni.ticketflow.application.core.domain.TicketHistory;
import com.moroni.ticketflow.application.core.domain.TicketHistoryEventType;
import com.moroni.ticketflow.application.core.domain.User;
import com.moroni.ticketflow.application.core.domain.UserRole;
import com.moroni.ticketflow.application.core.exception.InvalidTechnicianException;
import com.moroni.ticketflow.application.core.exception.TicketNotFoundException;
import com.moroni.ticketflow.application.core.exception.UserNotFoundException;
import com.moroni.ticketflow.application.ports.in.AssignTicketInputPort;
import com.moroni.ticketflow.application.ports.out.TicketHistoryRepositoryOutputPort;
import com.moroni.ticketflow.application.ports.out.TicketRepositoryOutputPort;
import com.moroni.ticketflow.application.ports.out.UserRepositoryOutputPort;

import java.util.UUID;

public class AssignTicketUseCase implements AssignTicketInputPort {

    private final TicketRepositoryOutputPort ticketRepositoryOutputPort;
    private final UserRepositoryOutputPort userRepositoryOutputPort;
    private final TicketHistoryRepositoryOutputPort ticketHistoryRepositoryOutputPort;

    public AssignTicketUseCase(
            TicketRepositoryOutputPort ticketRepositoryOutputPort,
            UserRepositoryOutputPort userRepositoryOutputPort,
            TicketHistoryRepositoryOutputPort ticketHistoryRepositoryOutputPort
    ) {
        this.ticketRepositoryOutputPort = ticketRepositoryOutputPort;
        this.userRepositoryOutputPort = userRepositoryOutputPort;
        this.ticketHistoryRepositoryOutputPort = ticketHistoryRepositoryOutputPort;
    }

    @Override
    public Ticket assign(UUID ticketId, UUID technicianId) {
        Ticket ticket = ticketRepositoryOutputPort.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException(ticketId));

        User technician = userRepositoryOutputPort.findById(technicianId)
                .orElseThrow(() -> new UserNotFoundException(technicianId));

        if (technician.getRole() != UserRole.TECHNICIAN && technician.getRole() != UserRole.ADMIN) {
            throw new InvalidTechnicianException(technicianId);
        }

        ticket.assignTo(technicianId);

        Ticket savedTicket = ticketRepositoryOutputPort.save(ticket);

        TicketHistory history = TicketHistory.create(
                savedTicket.getId(),
                TicketHistoryEventType.TECHNICIAN_ASSIGNED,
                "Ticket assigned to technician: " + technicianId
        );

        ticketHistoryRepositoryOutputPort.save(history);

        return savedTicket;
    }
}