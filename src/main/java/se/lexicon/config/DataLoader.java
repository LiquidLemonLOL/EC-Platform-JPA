package se.lexicon.config;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;
import se.lexicon.entity.Category;
import se.lexicon.entity.Product;
import se.lexicon.repo.CategoryRepository;
import se.lexicon.repo.ProductRepository;

import java.math.BigDecimal;

@Component
@Profile("!test")
public class DataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public DataLoader(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
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

        productRepository.save(new Product("Pizza", new BigDecimal("49.99"), foods));
        productRepository.save(new Product("Controller", new BigDecimal("299.99"), electronics));
        productRepository.save(new Product("Harry Potter 1", new BigDecimal("139.99"), books));
        productRepository.save(new Product("Squeaky giraffe", new BigDecimal("24.99"),  toys));
        productRepository.save(new Product("Pink T-Shirt", new BigDecimal("59.99"), clothes));
    }

}
