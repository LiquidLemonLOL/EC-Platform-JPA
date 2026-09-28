package se.lexicon.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank(message = "Name is required")
        @Size(max = 100)
        String name,

        @NotNull(message = "Price cannot be null")
        @DecimalMin(value = "0.0", inclusive = false)
        @Digits(fraction = 2, integer = 8)
        @Size(max = 60)
        BigDecimal price,

        @NotNull(message = "Category ID cannot be null")
        Long categoryId
) {}
