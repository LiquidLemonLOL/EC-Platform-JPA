package se.lexicon.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.dto.*;
import se.lexicon.entity.OrderStatus;
import se.lexicon.service.CategoryService;
import se.lexicon.service.CustomerService;
import se.lexicon.service.OrderService;
import se.lexicon.service.ProductService;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Test
    void placeOrderSuccessTest() {

        //create customer
        CustomerResponse customer = customerService.register(
                new CustomerRequest(
                        "John",
                        "Doe",
                        "john@example.com",
                        "password123",
                        "Main Street 1",
                        "Gothenburg",
                        "41101"
                )
        );

        // create category
        CategoryResponse category =
                categoryService.create("Computers");

        // create product
        ProductResponse product = productService.create(
                new ProductRequest(
                        "Laptop",
                        new BigDecimal("759.99"),
                        category.id()
                )
        );

        ProductResponse product2 = productService.create(
                new ProductRequest(
                        "Desktop",
                        new BigDecimal("999.99"),
                        category.id()
                )
        );

        // create order
        OrderRequest order = new OrderRequest(
                customer.id(),
                List.of(
                        new OrderItemRequest(
                                product.id(),
                                2
                        ),
                        new OrderItemRequest(
                                product2.id(),
                                1
                        )
                )
        );

        // place order

        OrderResponse orderResponse = orderService.placeOrder(order);

        // assert response is created
        assertNotNull(orderResponse);
        assertNotNull(orderResponse.id());
        assertNotNull(orderResponse.orderDate());


        // assert values
        assertEquals(customer.id(), orderResponse.customerId());
        assertEquals("John Doe", orderResponse.customerName());
        assertEquals(OrderStatus.CREATED, orderResponse.status());
        assertEquals(new BigDecimal("2519.97"), orderResponse.total());
        assertEquals(2, orderResponse.items().size());
        OrderItemResponse item = orderResponse.items().get(0);
        OrderItemResponse item2 = orderResponse.items().get(1);

        assertEquals(product.id(), item.productId());
        assertEquals(2, item.quantity());
        assertEquals(product2.id(), item2.productId());
        assertEquals(1, item2.quantity());
        assertEquals(new BigDecimal("759.99"), item.priceAtPurchase());
        assertEquals(new BigDecimal("999.99"), item2.priceAtPurchase());


    }

}
