package com.moroni.ticketflow.application.ports.in;

import com.moroni.ticketflow.application.core.domain.User;

import java.util.UUID;

public interface AddTechnicianToSupportQueueInputPort {

    User addTechnician(
            UUID supportQueueId,
            UUID technicianId
    );
}