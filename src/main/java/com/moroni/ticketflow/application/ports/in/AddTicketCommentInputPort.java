package com.moroni.ticketflow.application.ports.in;

import com.moroni.ticketflow.application.core.domain.TicketComment;
import com.moroni.ticketflow.application.core.domain.UserRole;

import java.util.UUID;

public interface AddTicketCommentInputPort {

    TicketComment addComment(
            UUID ticketId,
            UUID authorUserId,
            UserRole authenticatedUserRole,
            String message
    );
}