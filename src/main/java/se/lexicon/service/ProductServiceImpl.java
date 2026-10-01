package se.lexicon.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.dto.ProductRequest;
import se.lexicon.dto.ProductResponse;
import se.lexicon.entity.Category;
import se.lexicon.entity.Product;
import se.lexicon.exception.KeyNotFoundException;
import se.lexicon.mapper.ProductMapper;
import se.lexicon.repo.CategoryRepository;
import se.lexicon.repo.ProductRepository;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = new ProductMapper();
    }

    @Override
    @Transactional
    public ProductResponse create(ProductRequest request) {
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new KeyNotFoundException("Category not found: " + request.categoryId()));

        Product product = productRepository.save(productMapper.toEntity(request, category));
        return productMapper.toProductResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream()
                .map(productMapper::toProductResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> searchByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name).stream()
                .map(productMapper::toProductResponse)
                .toList();
    }

}
