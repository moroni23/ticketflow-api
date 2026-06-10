package com.moroni.ticketflow.application.core.usecase;

import com.moroni.ticketflow.application.core.domain.Category;
import com.moroni.ticketflow.application.core.exception.CategoryAlreadyExistsException;
import com.moroni.ticketflow.application.ports.in.CreateCategoryInputPort;
import com.moroni.ticketflow.application.ports.out.CategoryRepositoryOutputPort;

public class CreateCategoryUseCase implements CreateCategoryInputPort {

    private final CategoryRepositoryOutputPort categoryRepositoryOutputPort;

    public CreateCategoryUseCase(CategoryRepositoryOutputPort categoryRepositoryOutputPort) {
        this.categoryRepositoryOutputPort = categoryRepositoryOutputPort;
    }

    @Override
    public Category create(String name, String description) {
        String normalizedName = name.trim();

        if (categoryRepositoryOutputPort.existsByName(normalizedName)) {
            throw new CategoryAlreadyExistsException(normalizedName);
        }

        Category category = Category.create(normalizedName, description);

        return categoryRepositoryOutputPort.save(category);
    }
}
