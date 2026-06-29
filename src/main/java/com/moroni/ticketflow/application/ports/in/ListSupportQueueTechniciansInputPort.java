package com.moroni.ticketflow.application.ports.in;

import com.moroni.ticketflow.application.core.domain.User;

import java.util.List;
import java.util.UUID;

public interface ListSupportQueueTechniciansInputPort {

    List<User> listTechnicians(UUID supportQueueId);
}