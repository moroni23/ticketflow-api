package com.moroni.ticketflow.application.ports.in;

import com.moroni.ticketflow.application.core.domain.Category;

import java.util.List;

public interface ListCategoriesInputPort {

    List<Category> findAll();
}
