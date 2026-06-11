package com.moroni.ticketflow.application.core.usecase;

import com.moroni.ticketflow.application.core.domain.TicketHistory;
import com.moroni.ticketflow.application.core.domain.UserRole;
import com.moroni.ticketflow.application.ports.in.ListTicketHistoryInputPort;
import com.moroni.ticketflow.application.ports.out.TicketHistoryRepositoryOutputPort;

import java.util.List;
import java.util.UUID;

public class ListTicketHistoryUseCase implements ListTicketHistoryInputPort {

    private final TicketHistoryRepositoryOutputPort ticketHistoryRepositoryOutputPort;
    private final ValidateTicketAccessUseCase validateTicketAccessUseCase;

    public ListTicketHistoryUseCase(
            TicketHistoryRepositoryOutputPort ticketHistoryRepositoryOutputPort,
            ValidateTicketAccessUseCase validateTicketAccessUseCase
    ) {
        this.ticketHistoryRepositoryOutputPort = ticketHistoryRepositoryOutputPort;
        this.validateTicketAccessUseCase = validateTicketAccessUseCase;
    }

    @Override
    public List<TicketHistory> listByTicketId(
            UUID ticketId,
            UUID authenticatedUserId,
            UserRole authenticatedUserRole
    ) {
        validateTicketAccessUseCase.validate(
                ticketId,
                authenticatedUserId,
                authenticatedUserRole
        );

        return ticketHistoryRepositoryOutputPort.findByTicketId(ticketId);
    }

    @Override
    public List<TicketHistory> list(
            UUID ticketId,
            UUID authenticatedUserId,
            UserRole authenticatedUserRole
    ) {
        return listByTicketId(
                ticketId,
                authenticatedUserId,
                authenticatedUserRole
        );
    }
}