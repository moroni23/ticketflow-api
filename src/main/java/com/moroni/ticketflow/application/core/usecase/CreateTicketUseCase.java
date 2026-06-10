package com.moroni.ticketflow.application.core.usecase;

import com.moroni.ticketflow.application.core.domain.Ticket;
import com.moroni.ticketflow.application.core.domain.TicketHistory;
import com.moroni.ticketflow.application.core.domain.TicketHistoryEventType;
import com.moroni.ticketflow.application.core.domain.TicketPriority;
import com.moroni.ticketflow.application.core.exception.CategoryNotFoundException;
import com.moroni.ticketflow.application.core.exception.SupportQueueNotFoundException;
import com.moroni.ticketflow.application.core.exception.UserNotFoundException;
import com.moroni.ticketflow.application.ports.in.CreateTicketInputPort;
import com.moroni.ticketflow.application.ports.out.CategoryRepositoryOutputPort;
import com.moroni.ticketflow.application.ports.out.SupportQueueRepositoryOutputPort;
import com.moroni.ticketflow.application.ports.out.TicketHistoryRepositoryOutputPort;
import com.moroni.ticketflow.application.ports.out.TicketRepositoryOutputPort;
import com.moroni.ticketflow.application.ports.out.UserRepositoryOutputPort;

import java.util.UUID;

public class CreateTicketUseCase implements CreateTicketInputPort {

    private final TicketRepositoryOutputPort ticketRepositoryOutputPort;
    private final UserRepositoryOutputPort userRepositoryOutputPort;
    private final CategoryRepositoryOutputPort categoryRepositoryOutputPort;
    private final SupportQueueRepositoryOutputPort supportQueueRepositoryOutputPort;
    private final TicketHistoryRepositoryOutputPort ticketHistoryRepositoryOutputPort;

    public CreateTicketUseCase(
            TicketRepositoryOutputPort ticketRepositoryOutputPort,
            UserRepositoryOutputPort userRepositoryOutputPort,
            CategoryRepositoryOutputPort categoryRepositoryOutputPort,
            SupportQueueRepositoryOutputPort supportQueueRepositoryOutputPort,
            TicketHistoryRepositoryOutputPort ticketHistoryRepositoryOutputPort
    ) {
        this.ticketRepositoryOutputPort = ticketRepositoryOutputPort;
        this.userRepositoryOutputPort = userRepositoryOutputPort;
        this.categoryRepositoryOutputPort = categoryRepositoryOutputPort;
        this.supportQueueRepositoryOutputPort = supportQueueRepositoryOutputPort;
        this.ticketHistoryRepositoryOutputPort = ticketHistoryRepositoryOutputPort;
    }

    @Override
    public Ticket create(
            String title,
            String description,
            TicketPriority priority,
            UUID createdByUserId,
            UUID categoryId,
            UUID supportQueueId
    ) {
        if (!userRepositoryOutputPort.existsById(createdByUserId)) {
            throw new UserNotFoundException(createdByUserId);
        }

        if (!categoryRepositoryOutputPort.existsById(categoryId)) {
            throw new CategoryNotFoundException(categoryId);
        }

        if (!supportQueueRepositoryOutputPort.existsById(supportQueueId)) {
            throw new SupportQueueNotFoundException(supportQueueId);
        }

        Ticket ticket = Ticket.create(
                title,
                description,
                priority,
                createdByUserId,
                categoryId,
                supportQueueId
        );

        Ticket savedTicket = ticketRepositoryOutputPort.save(ticket);

        TicketHistory ticketHistory = TicketHistory.create(
                savedTicket.getId(),
                TicketHistoryEventType.TICKET_CREATED,
                "Ticket created"
        );

        ticketHistoryRepositoryOutputPort.save(ticketHistory);

        return savedTicket;
    }
}