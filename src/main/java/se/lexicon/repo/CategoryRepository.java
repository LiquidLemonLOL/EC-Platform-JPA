package se.lexicon.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.lexicon.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

    @Query("SELECT cat FROM Category cat WHERE cat.name LIKE %:key%")
    List<Category> selectByNameContaining(@Param("key") String key);

    // Count how many categories exist - JpaRepository already has support for counting all categories through
    // count() and is already declared by having the repository - no explicit statement needed, instead
    // I will add custom count.

    long countByNameContaining(String key);

}
