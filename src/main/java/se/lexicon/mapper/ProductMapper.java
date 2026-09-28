package se.lexicon.mapper;

import org.springframework.stereotype.Component;
import se.lexicon.dto.CategoryResponse;
import se.lexicon.dto.ProductRequest;
import se.lexicon.dto.ProductResponse;
import se.lexicon.entity.Category;
import se.lexicon.entity.Product;

@Component
public class ProductMapper {

    public ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCategory().getId(),
                product.getCategory().getName()
        );
    }

    public CategoryResponse toCategoryResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName()
        );
    }

    public Product toEntity(ProductRequest request, Category category) {
        return new Product(
                request.name(),
                request.price(),
                category
        );
    }

}
