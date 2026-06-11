package com.moroni.ticketflow.application.ports.in;

import com.moroni.ticketflow.application.core.domain.PageResult;
import com.moroni.ticketflow.application.core.domain.Ticket;

import java.util.UUID;

public interface SearchAssignedTicketsInputPort {

    PageResult<Ticket> searchAssignedToMe(
            String status,
            String priority,
            UUID supportQueueId,
            int page,
            int size,
            UUID authenticatedUserId
    );
}