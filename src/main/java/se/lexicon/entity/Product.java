package se.lexicon.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 100)
    private List<String> imageUrls;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "products_promotions",
            joinColumns = @JoinColumn(name = "promotion_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Promotion> promotions = new HashSet<>();

    public void addPromotion(Promotion promotion) {
        if (promotion == null) throw new IllegalArgumentException("Promotion cannot be null.");
        promotions.add(promotion);
    }

    public void removePromotion(Promotion promotion) {
        if (promotion == null) throw new IllegalArgumentException("Promotion cannot be null.");
        promotions.remove(promotion);
    }

}
