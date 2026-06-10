package com.moroni.ticketflow.application.core.usecase;

import com.moroni.ticketflow.application.core.domain.PageResult;
import com.moroni.ticketflow.application.core.domain.Ticket;
import com.moroni.ticketflow.application.core.domain.TicketPriority;
import com.moroni.ticketflow.application.core.domain.TicketStatus;
import com.moroni.ticketflow.application.core.domain.UserRole;
import com.moroni.ticketflow.application.core.exception.InvalidTicketPriorityException;
import com.moroni.ticketflow.application.core.exception.InvalidTicketStatusException;
import com.moroni.ticketflow.application.ports.in.SearchTicketsInputPort;
import com.moroni.ticketflow.application.ports.out.TicketRepositoryOutputPort;

import java.util.UUID;

public class SearchTicketsUseCase implements SearchTicketsInputPort {

    private final TicketRepositoryOutputPort ticketRepositoryOutputPort;

    public SearchTicketsUseCase(TicketRepositoryOutputPort ticketRepositoryOutputPort) {
        this.ticketRepositoryOutputPort = ticketRepositoryOutputPort;
    }

    @Override
    public PageResult<Ticket> search(
            String status,
            String priority,
            UUID supportQueueId,
            int page,
            int size,
            UUID authenticatedUserId,
            UserRole authenticatedUserRole
    ) {
        TicketStatus ticketStatus = parseStatus(status);
        TicketPriority ticketPriority = parsePriority(priority);

        int normalizedPage = normalizePage(page);
        int normalizedSize = normalizeSize(size);

        if (UserRole.USER.equals(authenticatedUserRole)) {
            return ticketRepositoryOutputPort.searchByCreatedByUserId(
                    ticketStatus,
                    ticketPriority,
                    supportQueueId,
                    authenticatedUserId,
                    normalizedPage,
                    normalizedSize
            );
        }

        return ticketRepositoryOutputPort.search(
                ticketStatus,
                ticketPriority,
                supportQueueId,
                normalizedPage,
                normalizedSize
        );
    }

    private TicketStatus parseStatus(String status) {
        if (status == null || status.isBlank()) {
            return null;
        }

        try {
            return TicketStatus.valueOf(status.trim().toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new InvalidTicketStatusException(status);
        }
    }

    private TicketPriority parsePriority(String priority) {
        if (priority == null || priority.isBlank()) {
            return null;
        }

        try {
            return TicketPriority.valueOf(priority.trim().toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new InvalidTicketPriorityException(priority);
        }
    }

    private int normalizePage(int page) {
        if (page < 0) {
            return 0;
        }

        return page;
    }

    private int normalizeSize(int size) {
        if (size <= 0) {
            return 10;
        }

        if (size > 50) {
            return 50;
        }

        return size;
    }
}