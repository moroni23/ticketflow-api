package com.moroni.ticketflow.application.core.usecase;

import com.moroni.ticketflow.application.core.domain.Ticket;
import com.moroni.ticketflow.application.core.domain.TicketHistory;
import com.moroni.ticketflow.application.core.domain.TicketHistoryEventType;
import com.moroni.ticketflow.application.core.domain.User;
import com.moroni.ticketflow.application.core.domain.UserRole;
import com.moroni.ticketflow.application.core.exception.InvalidTechnicianException;
import com.moroni.ticketflow.application.core.exception.SupportQueueNotFoundException;
import com.moroni.ticketflow.application.core.exception.TicketNotFoundException;
import com.moroni.ticketflow.application.core.exception.UserNotFoundException;
import com.moroni.ticketflow.application.ports.in.AssignTicketInputPort;
import com.moroni.ticketflow.application.ports.out.SupportQueueTechnicianRepositoryOutputPort;
import com.moroni.ticketflow.application.ports.out.TicketHistoryRepositoryOutputPort;
import com.moroni.ticketflow.application.ports.out.TicketRepositoryOutputPort;
import com.moroni.ticketflow.application.ports.out.UserRepositoryOutputPort;

import java.util.UUID;

public class AssignTicketUseCase implements AssignTicketInputPort {

    private final TicketRepositoryOutputPort ticketRepositoryOutputPort;
    private final UserRepositoryOutputPort userRepositoryOutputPort;
    private final TicketHistoryRepositoryOutputPort ticketHistoryRepositoryOutputPort;
    private final SupportQueueTechnicianRepositoryOutputPort supportQueueTechnicianRepositoryOutputPort;

    public AssignTicketUseCase(
            TicketRepositoryOutputPort ticketRepositoryOutputPort,
            UserRepositoryOutputPort userRepositoryOutputPort,
            TicketHistoryRepositoryOutputPort ticketHistoryRepositoryOutputPort,
            SupportQueueTechnicianRepositoryOutputPort supportQueueTechnicianRepositoryOutputPort
    ) {
        this.ticketRepositoryOutputPort = ticketRepositoryOutputPort;
        this.userRepositoryOutputPort = userRepositoryOutputPort;
        this.ticketHistoryRepositoryOutputPort = ticketHistoryRepositoryOutputPort;
        this.supportQueueTechnicianRepositoryOutputPort = supportQueueTechnicianRepositoryOutputPort;
    }

    @Override
    public Ticket assign(
            UUID ticketId,
            UUID technicianId
    ) {
        Ticket ticket = ticketRepositoryOutputPort.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException(ticketId));

        User technician = userRepositoryOutputPort.findById(technicianId)
                .orElseThrow(() -> new UserNotFoundException(technicianId));

        validateTechnicianRole(technician);

        validateTicketHasSupportQueue(ticket);

        validateTechnicianBelongsToTicketSupportQueue(
                ticket,
                technicianId
        );

        ticket.assignTo(technicianId);

        Ticket savedTicket = ticketRepositoryOutputPort.save(ticket);

        TicketHistory ticketHistory = TicketHistory.create(
                savedTicket.getId(),
                TicketHistoryEventType.TECHNICIAN_ASSIGNED,
                "Ticket assigned to technician: " + technician.getName()
        );

        ticketHistoryRepositoryOutputPort.save(ticketHistory);

        return savedTicket;
    }

    private void validateTechnicianRole(User technician) {
        if (!UserRole.TECHNICIAN.equals(technician.getRole())
                && !UserRole.ADMIN.equals(technician.getRole())) {
            throw new InvalidTechnicianException(technician.getId());
        }
    }

    private void validateTicketHasSupportQueue(Ticket ticket) {
        if (ticket.getSupportQueueId() == null) {
            throw new SupportQueueNotFoundException(null);
        }
    }

    private void validateTechnicianBelongsToTicketSupportQueue(
            Ticket ticket,
            UUID technicianId
    ) {
        boolean technicianBelongsToSupportQueue =
                supportQueueTechnicianRepositoryOutputPort.existsBySupportQueueIdAndTechnicianId(
                        ticket.getSupportQueueId(),
                        technicianId
                );

        if (!technicianBelongsToSupportQueue) {
            throw new InvalidTechnicianException(technicianId);
        }
    }
}