package com.moroni.ticketflow.application.ports.in;

import com.moroni.ticketflow.application.core.domain.Ticket;

import java.util.UUID;

public interface AssignTicketInputPort {

    Ticket assign(UUID ticketId, UUID technicianId);
}