package com.moroni.ticketflow.application.ports.in;

import com.moroni.ticketflow.application.core.domain.SupportQueue;

public interface CreateSupportQueueInputPort {

    SupportQueue create(String name, String description);
}