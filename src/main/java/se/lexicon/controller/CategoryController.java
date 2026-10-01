package se.lexicon.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.lexicon.dto.CategoryRequest;
import se.lexicon.dto.CategoryResponse;
import se.lexicon.service.CategoryService;

import java.util.List;


@RestController
@RequestMapping("/api/v1/categories")
@Validated
@Tag(name = "Category Controller", description = "APIs for managing categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @Operation(summary = "Create a new category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Resource created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body provided"),
            @ApiResponse(responseCode = "409", description = "Duplicate resource found")
    })
    public ResponseEntity<CategoryResponse> create(@Valid @RequestBody CategoryRequest categoryRequest) {
        CategoryResponse createdCategory = categoryService.create(categoryRequest.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @GetMapping
    @Operation(summary = "List all categories")
    public ResponseEntity<List<CategoryResponse>> findAll() {return ResponseEntity.ok(categoryService.findAll());}





}
