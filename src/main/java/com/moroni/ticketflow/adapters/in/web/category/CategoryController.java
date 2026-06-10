package com.moroni.ticketflow.adapters.in.web.category;

import com.moroni.ticketflow.adapters.in.web.category.request.CreateCategoryRequest;
import com.moroni.ticketflow.adapters.in.web.category.response.CategoryResponse;
import com.moroni.ticketflow.application.core.domain.Category;
import com.moroni.ticketflow.application.ports.in.CreateCategoryInputPort;
import com.moroni.ticketflow.application.ports.in.ListCategoriesInputPort;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CreateCategoryInputPort createCategoryInputPort;
    private final ListCategoriesInputPort listCategoriesInputPort;

    public CategoryController(
            CreateCategoryInputPort createCategoryInputPort,
            ListCategoriesInputPort listCategoriesInputPort
    ) {
        this.createCategoryInputPort = createCategoryInputPort;
        this.listCategoriesInputPort = listCategoriesInputPort;
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> create(
            @RequestBody @Valid CreateCategoryRequest request
    ) {
        Category category = createCategoryInputPort.create(
                request.getName(),
                request.getDescription()
        );

        CategoryResponse response = CategoryResponse.fromDomain(category);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findAll() {
        List<CategoryResponse> response = listCategoriesInputPort.findAll()
                .stream()
                .map(CategoryResponse::fromDomain)
                .toList();

        return ResponseEntity.ok(response);
    }
}