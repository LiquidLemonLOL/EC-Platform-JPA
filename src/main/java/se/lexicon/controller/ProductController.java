package se.lexicon.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import se.lexicon.dto.ProductRequest;
import se.lexicon.dto.ProductResponse;
import se.lexicon.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@Validated
@Tag(name = "Product Controller", description = "APIs for managing products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @Operation(summary = "Create a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Resource created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body provided"),
            @ApiResponse(responseCode = "409", description = "Duplicate resource found")
    })
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        ProductResponse createdProduct = productService.create(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @GetMapping
    @Operation(summary = "List all products")
    public ResponseEntity<List<ProductResponse>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/search")
    @Operation(summary = "Search product by name")
    public ResponseEntity<List<ProductResponse>> findByName(@RequestParam @NotBlank String name) {
        return ResponseEntity.ok(productService.searchByName(name));
    }


}
