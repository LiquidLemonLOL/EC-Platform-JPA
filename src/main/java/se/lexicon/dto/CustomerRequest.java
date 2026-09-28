package se.lexicon.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CustomerRequest(
        @NotBlank(message = "First name is required")
        @Size(max = 60)
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(max = 60)
        String lastName,

        @NotBlank(message = "Email is required")
        @Size(max = 100)
        @Email
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 6, max = 60)
        String password,

        @NotBlank(message = "Street is required")
        @Size(min = 6, max = 60)
        String street,

        @NotBlank(message = "City is required")
        @Size(min = 6, max = 60)
        String city,

        @NotBlank(message = "Zip code is required")
        @Size(min = 6, max = 60)
        String zipCode
) {}
