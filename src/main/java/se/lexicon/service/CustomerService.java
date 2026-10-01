package se.lexicon.service;

import se.lexicon.dto.CustomerRequest;
import se.lexicon.dto.CustomerResponse;

import java.util.Optional;


public interface CustomerService {

    CustomerResponse register(CustomerRequest request);

    Optional<CustomerResponse> findById(Long id);

    CustomerResponse update(Long id, CustomerRequest request);

}
