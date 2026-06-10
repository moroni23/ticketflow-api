package com.moroni.ticketflow.application.core.usecase;

import com.moroni.ticketflow.application.core.domain.Ticket;
import com.moroni.ticketflow.application.ports.in.ListTicketsInputPort;
import com.moroni.ticketflow.application.ports.out.TicketRepositoryOutputPort;

import java.util.List;

public class ListTicketsUseCase implements ListTicketsInputPort {

    private final TicketRepositoryOutputPort ticketRepositoryOutputPort;

    public ListTicketsUseCase(TicketRepositoryOutputPort ticketRepositoryOutputPort) {
        this.ticketRepositoryOutputPort = ticketRepositoryOutputPort;
    }

    @Override
    public List<Ticket> findAll() {
        return ticketRepositoryOutputPort.findAll();
    }
}