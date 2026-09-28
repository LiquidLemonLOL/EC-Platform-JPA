package se.lexicon.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.dto.CategoryResponse;
import se.lexicon.entity.Category;
import se.lexicon.exception.DuplicateFoundException;
import se.lexicon.mapper.ProductMapper;
import se.lexicon.repo.CategoryRepository;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductMapper productMapper) {
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    @Override
    @Transactional
    public CategoryResponse create(String name) {
        String cleanName = name == null ? "" : name.trim();
        if (cleanName.isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }
        if (categoryRepository.existsByNameIgnoreCase(cleanName)) {
            throw new DuplicateFoundException("Category with name " + cleanName + " already exists");
        }

        Category category = categoryRepository.save(new Category(cleanName));

        return productMapper.toCategoryResponse(category);
    }

    @Override
    @Transactional
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll().stream()
            .map(productMapper::toCategoryResponse)
            .toList();
    }
}
