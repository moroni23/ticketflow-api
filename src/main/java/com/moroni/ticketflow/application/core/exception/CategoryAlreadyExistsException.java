package com.moroni.ticketflow.application.core.exception;

public class CategoryAlreadyExistsException extends RuntimeException {

    public CategoryAlreadyExistsException(String name) {
        super("Category with name already exists: " + name);
    }
}