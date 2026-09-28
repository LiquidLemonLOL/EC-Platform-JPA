package se.lexicon.test;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.dto.CategoryResponse;
import se.lexicon.service.CategoryService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void testCreate() {
        CategoryResponse response = categoryService.create("Computers");

        assertNotNull(response);
        assertNotNull(response.id());
        assertEquals("Computers", response.name());
    }

    @Test
    public void testFindAll() {
        List<CategoryResponse> response = categoryService.findAll();

        assertFalse(response.isEmpty());
    }

}
