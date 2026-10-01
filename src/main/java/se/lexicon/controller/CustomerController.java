package se.lexicon.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import se.lexicon.dto.CustomerRequest;
import se.lexicon.dto.CustomerResponse;
import se.lexicon.exception.KeyNotFoundException;
import se.lexicon.service.CustomerService;


@RestController
@RequestMapping("/api/v1/customers")
@Validated
@Tag(name = "Customer Controller", description = "APIs for managing customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @Operation(summary = "Register a new customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Resource created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body provided"),
            @ApiResponse(responseCode = "409", description = "Duplicate resource found")

    })
    public ResponseEntity<CustomerResponse> registerCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
            CustomerResponse regCustomer = customerService.register(customerRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(regCustomer);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Find a customer by ID")
    public ResponseEntity<CustomerResponse> findById(@PathVariable @Positive Long id) {
        return customerService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new KeyNotFoundException("Customer not found : " + id ));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a customer")
    public ResponseEntity<CustomerResponse> update(@PathVariable @Positive Long id, @Valid @RequestBody CustomerRequest customerRequest) {
        CustomerResponse updateCustomer = customerService.update(id, customerRequest);
        return ResponseEntity.ok(updateCustomer);
    }


}
