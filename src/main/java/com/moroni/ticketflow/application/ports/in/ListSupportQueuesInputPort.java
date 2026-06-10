package com.moroni.ticketflow.application.ports.in;

import com.moroni.ticketflow.application.core.domain.SupportQueue;

import java.util.List;

public interface ListSupportQueuesInputPort {

    List<SupportQueue> findAll();
}