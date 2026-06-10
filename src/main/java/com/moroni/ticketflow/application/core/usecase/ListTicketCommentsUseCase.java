package com.moroni.ticketflow.application.core.usecase;

import com.moroni.ticketflow.application.core.domain.TicketComment;
import com.moroni.ticketflow.application.core.domain.UserRole;
import com.moroni.ticketflow.application.ports.in.ListTicketCommentsInputPort;
import com.moroni.ticketflow.application.ports.out.TicketCommentRepositoryOutputPort;

import java.util.List;
import java.util.UUID;

public class ListTicketCommentsUseCase implements ListTicketCommentsInputPort {

    private final TicketCommentRepositoryOutputPort ticketCommentRepositoryOutputPort;
    private final ValidateTicketAccessUseCase validateTicketAccessUseCase;

    public ListTicketCommentsUseCase(
            TicketCommentRepositoryOutputPort ticketCommentRepositoryOutputPort,
            ValidateTicketAccessUseCase validateTicketAccessUseCase
    ) {
        this.ticketCommentRepositoryOutputPort = ticketCommentRepositoryOutputPort;
        this.validateTicketAccessUseCase = validateTicketAccessUseCase;
    }

    @Override
    public List<TicketComment> listByTicketId(
            UUID ticketId,
            UUID authenticatedUserId,
            UserRole authenticatedUserRole
    ) {
        validateTicketAccessUseCase.validateAccess(
                ticketId,
                authenticatedUserId,
                authenticatedUserRole
        );

        return ticketCommentRepositoryOutputPort.findByTicketIdOrderByCreatedAtAsc(ticketId);
    }
}