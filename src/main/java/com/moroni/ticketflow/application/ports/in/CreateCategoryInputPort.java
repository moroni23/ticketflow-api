package com.moroni.ticketflow.application.ports.in;

import com.moroni.ticketflow.application.core.domain.Category;

public interface CreateCategoryInputPort {

    Category create(String name, String description);
}
