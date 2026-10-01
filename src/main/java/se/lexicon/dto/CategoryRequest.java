package se.lexicon.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequest(
    @NotBlank
    @Size(min = 1, max = 100)
    String name
) {}
