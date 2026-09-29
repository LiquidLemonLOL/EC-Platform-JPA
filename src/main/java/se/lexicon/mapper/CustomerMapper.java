package se.lexicon.mapper;

import org.springframework.stereotype.Component;
import se.lexicon.dto.AddressResponse;
import se.lexicon.dto.CustomerRequest;
import se.lexicon.dto.CustomerResponse;
import se.lexicon.entity.Address;
import se.lexicon.entity.Customer;

@Component
public class CustomerMapper {

    // extracts the data from an address to simplify toEntity for customer
    public AddressResponse toAddressResponse(Address address) {
        if (address == null) {
            return null;
        }
        return new AddressResponse(
                address.getStreet(),
                address.getCity(),
                address.getZipCode()
        );
    }

    public CustomerResponse toCustomerResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstName() + " " + customer.getLastName(),
                customer.getEmail(),
                toAddressResponse(customer.getAddress())
        );
    }

    public Customer toEntity(CustomerRequest request) {
        Customer customer = new Customer();
        customer.setFirstName(request.firstName());
        customer.setLastName(request.lastName());
        customer.setEmail(request.email());
        customer.setPassword(request.password());
        customer.setAddress(new Address(request.street(), request.city(), request.zipCode())
        );
        return customer;
    }

}
