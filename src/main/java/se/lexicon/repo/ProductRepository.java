package se.lexicon.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import se.lexicon.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategoryName(String categoryName);

    List<Product> findByPriceBetween(BigDecimal lowPrice, BigDecimal highPrice);

    List<Product> findByNameContaining(String key);

    List<Product> findByPriceLessThanEqual(BigDecimal price);

    List<Product> findAllByOrderByPriceDesc();

    List<Product> findAllByOrderByPriceAsc();

    long countProductsInCategory(String categoryName);

    List<Product> findByCategoryId(long id);

}
