package se.lexicon.repo;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import se.lexicon.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Override
    @EntityGraph(attributePaths = "category")
    List<Product> findAll();

    List<Product> findByCategoryName(String categoryName);

    List<Product> findByPriceBetween(BigDecimal lowPrice, BigDecimal highPrice);

    @EntityGraph(attributePaths = "category")
    List<Product> findByNameContainingIgnoreCase(String key);

    List<Product> findByPriceLessThanEqual(BigDecimal price);

    List<Product> findAllByOrderByPriceDesc();

    List<Product> findAllByOrderByPriceAsc();

    long countByCategoryId(Long categoryId);

    List<Product> findByCategoryId(long id);

}
