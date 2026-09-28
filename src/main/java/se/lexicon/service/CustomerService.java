package se.lexicon.service;

import se.lexicon.dto.CustomerRequest;
import se.lexicon.dto.CustomerResponse;


public interface CustomerService {

    CustomerResponse register(CustomerRequest request);

    CustomerResponse findById(Long id);

    CustomerResponse update(Long id, CustomerRequest request);

}
