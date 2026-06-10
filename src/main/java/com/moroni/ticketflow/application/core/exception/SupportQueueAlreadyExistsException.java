package com.moroni.ticketflow.application.core.exception;

public class SupportQueueAlreadyExistsException extends RuntimeException {

    public SupportQueueAlreadyExistsException(String name) {
        super("Support queue already exists with name: " + name);
    }
}
