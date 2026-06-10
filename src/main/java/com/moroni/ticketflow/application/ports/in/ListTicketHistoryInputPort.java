package com.moroni.ticketflow.application.ports.in;

import com.moroni.ticketflow.application.core.domain.TicketHistory;
import com.moroni.ticketflow.application.core.domain.UserRole;

import java.util.List;
import java.util.UUID;

public interface ListTicketHistoryInputPort {

    List<TicketHistory> listByTicketId(
            UUID ticketId,
            UUID authenticatedUserId,
            UserRole authenticatedUserRole
    );
}