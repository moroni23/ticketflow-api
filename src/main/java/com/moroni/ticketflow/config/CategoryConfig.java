package com.moroni.ticketflow.config;

import com.moroni.ticketflow.application.core.usecase.CreateCategoryUseCase;
import com.moroni.ticketflow.application.ports.out.CategoryRepositoryOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.moroni.ticketflow.application.core.usecase.ListCategoriesUseCase;

@Configuration
public class CategoryConfig {

    @Bean
    public CreateCategoryUseCase createCategoryUseCase(
            CategoryRepositoryOutputPort categoryRepositoryOutputPort
    ) {
        return new CreateCategoryUseCase(categoryRepositoryOutputPort);
    }

    @Bean
    public ListCategoriesUseCase listCategoriesUseCase(
            CategoryRepositoryOutputPort categoryRepositoryOutputPort
    ) {
        return new ListCategoriesUseCase(categoryRepositoryOutputPort);
    }
}