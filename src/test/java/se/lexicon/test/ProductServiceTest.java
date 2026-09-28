package se.lexicon.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.dto.CategoryResponse;
import se.lexicon.dto.ProductRequest;
import se.lexicon.dto.ProductResponse;
import se.lexicon.service.CategoryService;
import se.lexicon.service.ProductService;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ProductServiceTest {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @Test
    void categoryWithProductTest() {

        CategoryResponse category = categoryService.create("Computers");

        ProductRequest productRequest = new ProductRequest(
                "Laptop",
                new BigDecimal("759.99"),
                category.id()
        );

        ProductResponse product = productService.create(productRequest);

        assertNotNull(product.id());
        assertEquals("Laptop", product.name());
        assertEquals("Computers", product.categoryName());

        List<ProductResponse> found = productService.searchByName("LAP");

        assertEquals(1, found.size());
        assertEquals("Laptop", found.getFirst().name());
        assertEquals("Computers", found.getFirst().categoryName());
    }



}
