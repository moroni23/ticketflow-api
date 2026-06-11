package com.moroni.ticketflow.application.core.usecase;

import com.moroni.ticketflow.application.core.domain.SupportQueue;
import com.moroni.ticketflow.application.core.domain.Ticket;
import com.moroni.ticketflow.application.core.domain.TicketHistory;
import com.moroni.ticketflow.application.core.domain.TicketHistoryEventType;
import com.moroni.ticketflow.application.core.domain.UserRole;
import com.moroni.ticketflow.application.core.exception.SupportQueueNotFoundException;
import com.moroni.ticketflow.application.core.exception.TicketNotFoundException;
import com.moroni.ticketflow.application.ports.in.UpdateTicketSupportQueueInputPort;
import com.moroni.ticketflow.application.ports.out.SupportQueueRepositoryOutputPort;
import com.moroni.ticketflow.application.ports.out.TicketHistoryRepositoryOutputPort;
import com.moroni.ticketflow.application.ports.out.TicketRepositoryOutputPort;

import java.util.Objects;
import java.util.UUID;

public class UpdateTicketSupportQueueUseCase implements UpdateTicketSupportQueueInputPort {

    private final TicketRepositoryOutputPort ticketRepositoryOutputPort;
    private final SupportQueueRepositoryOutputPort supportQueueRepositoryOutputPort;
    private final TicketHistoryRepositoryOutputPort ticketHistoryRepositoryOutputPort;
    private final ValidateTicketTechnicianAccessUseCase validateTicketTechnicianAccessUseCase;

    public UpdateTicketSupportQueueUseCase(
            TicketRepositoryOutputPort ticketRepositoryOutputPort,
            SupportQueueRepositoryOutputPort supportQueueRepositoryOutputPort,
            TicketHistoryRepositoryOutputPort ticketHistoryRepositoryOutputPort,
            ValidateTicketTechnicianAccessUseCase validateTicketTechnicianAccessUseCase
    ) {
        this.ticketRepositoryOutputPort = ticketRepositoryOutputPort;
        this.supportQueueRepositoryOutputPort = supportQueueRepositoryOutputPort;
        this.ticketHistoryRepositoryOutputPort = ticketHistoryRepositoryOutputPort;
        this.validateTicketTechnicianAccessUseCase = validateTicketTechnicianAccessUseCase;
    }

    @Override
    public Ticket updateSupportQueue(
            UUID ticketId,
            UUID supportQueueId,
            UUID authenticatedUserId,
            UserRole authenticatedUserRole
    ) {
        Ticket ticket = ticketRepositoryOutputPort.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException(ticketId));

        validateTicketTechnicianAccessUseCase.validate(
                ticket,
                authenticatedUserId,
                authenticatedUserRole
        );

        SupportQueue newSupportQueue = supportQueueRepositoryOutputPort.findById(supportQueueId)
                .orElseThrow(() -> new SupportQueueNotFoundException(supportQueueId));

        if (Objects.equals(ticket.getSupportQueueId(), supportQueueId)) {
            return ticket;
        }

        String oldSupportQueueName = findSupportQueueName(ticket.getSupportQueueId());

        ticket.changeSupportQueue(supportQueueId);

        Ticket savedTicket = ticketRepositoryOutputPort.save(ticket);

        TicketHistory ticketHistory = TicketHistory.create(
                savedTicket.getId(),
                TicketHistoryEventType.SUPPORT_QUEUE_CHANGED,
                "Ticket moved from support queue '" + oldSupportQueueName + "' to '" + newSupportQueue.getName() + "'"
        );

        ticketHistoryRepositoryOutputPort.save(ticketHistory);

        return savedTicket;
    }

    private String findSupportQueueName(UUID supportQueueId) {
        if (supportQueueId == null) {
            return "None";
        }

        return supportQueueRepositoryOutputPort.findById(supportQueueId)
                .map(SupportQueue::getName)
                .orElse("Unknown");
    }
}