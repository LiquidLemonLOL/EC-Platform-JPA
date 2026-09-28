package se.lexicon.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.dto.CustomerRequest;
import se.lexicon.dto.CustomerResponse;
import se.lexicon.exception.DuplicateFoundException;
import se.lexicon.service.CustomerService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Test
    void registerCustomer() {
        CustomerRequest request = new CustomerRequest(
                "John",
                "Doe",
                "john@example.com",
                "password123",
                "Main Street 1",
                "Gothenburg",
                "41101"
        );

        CustomerResponse response = customerService.register(request);

        assertNotNull(response);
        assertEquals("John Doe", response.fullName());
        assertEquals("john@example.com", response.email());
    }

    @Test
    void findByIdTest() {
        CustomerRequest request = new CustomerRequest(
                "John",
                "Doe",
                "john@example.com",
                "password123",
                "Main Street 1",
                "Gothenburg",
                "41101"
        );

        CustomerResponse response = customerService.register(request);
        CustomerResponse found = customerService.findById(response.id());

        assertEquals(response.id(), found.id());
        assertEquals("John Doe", found.fullName());
        assertEquals("john@example.com", found.email());
    }

    @Test
    void updateCustomer() {
        CustomerRequest request = new CustomerRequest(
                "John",
                "Doe",
                "john@example.com",
                "password123",
                "Main Street 1",
                "Gothenburg",
                "41101"
        );

        CustomerResponse created = customerService.register(request);

        CustomerRequest updateRequest = new CustomerRequest(
                "Johanna",
                "Doebby",
                "johanna@example.com",
                "password456",
                "Main Street 45",
                "Gothenburgia",
                "41404"
        );

        CustomerResponse update = customerService.update(created.id(), updateRequest);

        assertEquals(update.id(), created.id());
        assertEquals("johanna@example.com", update.email());
        assertEquals("Gothenburgia", update.addressResponse().city());
        assertEquals("41404", update.addressResponse().zipCode());
        assertEquals("Main Street 45", update.addressResponse().street());
        assertEquals("Johanna Doebby", update.fullName());

        CustomerResponse found = customerService.findById(created.id());
        assertEquals(created.id(), found.id());
        assertEquals("Johanna Doebby", found.fullName());
        assertEquals("johanna@example.com", found.email());
    }

    @Test
    void duplicateEmailTest() {

        CustomerRequest request = new CustomerRequest(
                "John",
                "Doe",
                "john@example.com",
                "password123",
                "Main Street 1",
                "Gothenburg",
                "41101"
        );

        customerService.register(request);

        CustomerRequest requestDuplicateEmail = new CustomerRequest(
                "Johnny",
                "Doeni",
                "john@example.com",
                "password1234",
                "Main Street 12",
                "Gothenburgen",
                "41102"
        );

        assertThrows(DuplicateFoundException.class,
                () -> customerService.register(requestDuplicateEmail)
        );
    }



}
