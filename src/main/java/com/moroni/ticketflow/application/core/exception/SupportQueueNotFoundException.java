package com.moroni.ticketflow.application.core.exception;

import java.util.UUID;

public class SupportQueueNotFoundException extends RuntimeException {

    public SupportQueueNotFoundException(UUID id) {
        super("Support queue not found with id: " + id);
    }
}