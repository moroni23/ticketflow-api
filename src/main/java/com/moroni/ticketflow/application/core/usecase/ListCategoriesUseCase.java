package com.moroni.ticketflow.application.core.usecase;

import com.moroni.ticketflow.application.core.domain.Category;
import com.moroni.ticketflow.application.ports.in.ListCategoriesInputPort;
import com.moroni.ticketflow.application.ports.out.CategoryRepositoryOutputPort;

import java.util.List;

public class ListCategoriesUseCase implements ListCategoriesInputPort {

    private final CategoryRepositoryOutputPort categoryRepositoryOutputPort;

    public ListCategoriesUseCase(CategoryRepositoryOutputPort categoryRepositoryOutputPort) {
        this.categoryRepositoryOutputPort = categoryRepositoryOutputPort;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepositoryOutputPort.findAll();
    }
}