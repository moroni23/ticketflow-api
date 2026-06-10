package com.moroni.ticketflow.application.ports.in;

import com.moroni.ticketflow.application.core.domain.PageResult;
import com.moroni.ticketflow.application.core.domain.Ticket;
import com.moroni.ticketflow.application.core.domain.UserRole;

import java.util.UUID;

public interface SearchTicketsInputPort {

    PageResult<Ticket> search(
            String status,
            String priority,
            UUID supportQueueId,
            int page,
            int size,
            UUID authenticatedUserId,
            UserRole authenticatedUserRole
    );
}