package se.lexicon.config;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;
import se.lexicon.entity.*;
import se.lexicon.repo.CategoryRepository;
import se.lexicon.repo.CustomerRepository;
import se.lexicon.repo.OrderRepository;
import se.lexicon.repo.ProductRepository;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;

@Component
@Profile("!test")
public class DataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final Clock clock;

    public DataLoader(CategoryRepository categoryRepository, ProductRepository productRepository,  CustomerRepository customerRepository, OrderRepository orderRepository, Clock clock) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.clock = clock;
    }

    @Override
    public void run(String... args) throws Exception {
        // if data exists, stop seeding
        if (categoryRepository.count() > 0) {
            return;
        }

        Category clothes = categoryRepository.save(new Category("Clothes"));
        Category foods = categoryRepository.save(new Category("Foods"));
        Category electronics = categoryRepository.save(new Category("Electronics"));
        Category books = categoryRepository.save(new Category("Books"));
        Category toys = categoryRepository.save(new Category("Toys"));

        Product pizza = productRepository.save(new Product("Pizza", new BigDecimal("49.99"), foods));
        Product controller = productRepository.save(new Product("Controller", new BigDecimal("299.99"), electronics));
        Product harryPotter = productRepository.save(new Product("Harry Potter 1", new BigDecimal("139.99"), books));
        Product squeakyGiraffe = productRepository.save(new Product("Squeaky giraffe", new BigDecimal("24.99"),  toys));
        Product pinkShirt = productRepository.save(new Product("Pink T-Shirt", new BigDecimal("59.99"), clothes));

        Customer john_doe = customerRepository.save(new Customer(
                "John",
                "Doe",
                "john@example.com",
                "password123",
                new Address(
                        "Main Street 1",
                        "Gothenburg",
                        "41101"
                )
        ));

        Customer johanna_doebby = customerRepository.save(new Customer(
                "Johanna",
                "Doebby",
                "johanna@example.com",
                "password456",
                new Address(
                        "Main Street 45",
                        "Gothenburgia",
                        "41404"
                )
        ));

        Order order1 = new Order();
        order1.setCustomer(john_doe);
        order1.setOrderDate(Instant.now(clock));
        order1.setStatus(OrderStatus.CREATED);
        order1.addItem(new OrderItem(controller, 1, controller.getPrice()));
        order1.addItem(new OrderItem(pinkShirt, 2, pinkShirt.getPrice()));
        orderRepository.save(order1);

        Order order2 = new Order();
        order2.setCustomer(johanna_doebby);
        order2.setOrderDate(Instant.now(clock));
        order2.setStatus(OrderStatus.CREATED);
        order2.addItem(new OrderItem(squeakyGiraffe, 3, squeakyGiraffe.getPrice()));
        order2.addItem(new OrderItem(harryPotter, 1, squeakyGiraffe.getPrice()));
        order2.addItem(new OrderItem(pizza, 2, pizza.getPrice()));
        orderRepository.save(order2);
    }


}
