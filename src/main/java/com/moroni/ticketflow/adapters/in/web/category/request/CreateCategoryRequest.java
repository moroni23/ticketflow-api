package com.moroni.ticketflow.adapters.in.web.category.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateCategoryRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must have at most 100 characters")
    private String name;

    @Size(max = 255, message = "Description must have at most 255 characters")
    private String description;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}

