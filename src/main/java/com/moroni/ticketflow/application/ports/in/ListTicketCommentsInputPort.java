package com.moroni.ticketflow.application.ports.in;

import com.moroni.ticketflow.application.core.domain.TicketComment;
import com.moroni.ticketflow.application.core.domain.UserRole;

import java.util.List;
import java.util.UUID;

public interface ListTicketCommentsInputPort {

    List<TicketComment> listByTicketId(
            UUID ticketId,
            UUID authenticatedUserId,
            UserRole authenticatedUserRole
    );
}