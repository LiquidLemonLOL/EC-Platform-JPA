package se.lexicon.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.dto.CustomerRequest;
import se.lexicon.dto.CustomerResponse;
import se.lexicon.entity.Address;
import se.lexicon.entity.Customer;
import se.lexicon.exception.DuplicateFoundException;
import se.lexicon.exception.KeyNotFoundException;
import se.lexicon.mapper.CustomerMapper;
import se.lexicon.repo.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public  CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    @Transactional
    public CustomerResponse register(CustomerRequest request) {
        if (customerRepository.existsByEmail(request.email())) {
            throw new DuplicateFoundException("Email already exists: " +  request.email());
        }
        Customer customer = customerRepository.save(customerMapper.toEntity(request));
        return customerMapper.toCustomerResponse(customer);
    }


    @Override
    @Transactional
    public CustomerResponse findById(Long id) {
        return customerMapper.toCustomerResponse(getCustomer(id));
    }

    @Override
    public CustomerResponse update(Long id, CustomerRequest request) {
        Customer customer = getCustomer(id);

        // if email exists and email of the customer does not equal the email of the update request
        // , as in if the update request is including an email change - check uniqueness of email
        if (customerRepository.existsByEmail(request.email()) && !customer.getEmail().equalsIgnoreCase(request.email())) {
            throw new DuplicateFoundException("Email already exists: " +  request.email());
        }

        customer.setFirstName(request.firstName());
        customer.setLastName(request.lastName());
        customer.setEmail(request.email());
        customer.setPassword(request.password());

        Address address = customer.getAddress();
        address.setStreet(request.street());
        address.setCity(request.city());
        address.setZipCode(request.zipCode());

        return customerMapper.toCustomerResponse(customer);
    }

    private Customer getCustomer(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new KeyNotFoundException("Customer with id " + id + " not found"));
    }

}
