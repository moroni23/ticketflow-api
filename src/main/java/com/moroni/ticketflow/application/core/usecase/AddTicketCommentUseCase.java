package com.moroni.ticketflow.application.core.usecase;

import com.moroni.ticketflow.application.core.domain.TicketComment;
import com.moroni.ticketflow.application.core.domain.TicketHistory;
import com.moroni.ticketflow.application.core.domain.TicketHistoryEventType;
import com.moroni.ticketflow.application.core.domain.UserRole;
import com.moroni.ticketflow.application.core.exception.UserNotFoundException;
import com.moroni.ticketflow.application.ports.in.AddTicketCommentInputPort;
import com.moroni.ticketflow.application.ports.out.TicketCommentRepositoryOutputPort;
import com.moroni.ticketflow.application.ports.out.TicketHistoryRepositoryOutputPort;
import com.moroni.ticketflow.application.ports.out.UserRepositoryOutputPort;

import java.util.UUID;

public class AddTicketCommentUseCase implements AddTicketCommentInputPort {

    private final TicketCommentRepositoryOutputPort ticketCommentRepositoryOutputPort;
    private final UserRepositoryOutputPort userRepositoryOutputPort;
    private final TicketHistoryRepositoryOutputPort ticketHistoryRepositoryOutputPort;
    private final ValidateTicketAccessUseCase validateTicketAccessUseCase;

    public AddTicketCommentUseCase(
            TicketCommentRepositoryOutputPort ticketCommentRepositoryOutputPort,
            UserRepositoryOutputPort userRepositoryOutputPort,
            TicketHistoryRepositoryOutputPort ticketHistoryRepositoryOutputPort,
            ValidateTicketAccessUseCase validateTicketAccessUseCase
    ) {
        this.ticketCommentRepositoryOutputPort = ticketCommentRepositoryOutputPort;
        this.userRepositoryOutputPort = userRepositoryOutputPort;
        this.ticketHistoryRepositoryOutputPort = ticketHistoryRepositoryOutputPort;
        this.validateTicketAccessUseCase = validateTicketAccessUseCase;
    }

    @Override
    public TicketComment addComment(
            UUID ticketId,
            UUID authorUserId,
            UserRole authenticatedUserRole,
            String message
    ) {
        validateTicketAccessUseCase.validateAccess(
                ticketId,
                authorUserId,
                authenticatedUserRole
        );

        if (userRepositoryOutputPort.findById(authorUserId).isEmpty()) {
            throw new UserNotFoundException(authorUserId);
        }

        TicketComment ticketComment = TicketComment.create(
                ticketId,
                authorUserId,
                message.trim()
        );

        TicketComment savedComment = ticketCommentRepositoryOutputPort.save(ticketComment);

        TicketHistory history = TicketHistory.create(
                ticketId,
                TicketHistoryEventType.COMMENT_ADDED,
                "Comment added by user: " + authorUserId
        );

        ticketHistoryRepositoryOutputPort.save(history);

        return savedComment;
    }
}