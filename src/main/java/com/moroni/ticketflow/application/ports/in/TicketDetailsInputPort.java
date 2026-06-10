package com.moroni.ticketflow.application.ports.in;

import com.moroni.ticketflow.application.core.domain.PageResult;
import com.moroni.ticketflow.application.core.domain.Ticket;
import com.moroni.ticketflow.application.core.domain.TicketDetails;

public interface TicketDetailsInputPort {

    TicketDetails enrich(Ticket ticket);

    PageResult<TicketDetails> enrichPage(PageResult<Ticket> ticketPage);
}