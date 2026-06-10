package com.moroni.ticketflow.application.ports.in;

import com.moroni.ticketflow.application.core.domain.Ticket;

import java.util.List;

public interface ListTicketsInputPort {

    List<Ticket> findAll();
}
