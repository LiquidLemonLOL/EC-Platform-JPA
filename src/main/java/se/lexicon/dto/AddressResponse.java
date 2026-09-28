package se.lexicon.dto;

public record AddressResponse (
        String street,
        String city,
        String zipCode
) {}
