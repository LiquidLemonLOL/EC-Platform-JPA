package se.lexicon.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")

@Entity
@Table(name = "promotions")
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    // max 100 = free product promotion
    @DecimalMin("0.00")
    @DecimalMax("100.00")
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal discountPercent;

    @Column(nullable = false)
    private LocalDate startDate;

    @ManyToMany(mappedBy = "promotions", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Product> products = new HashSet<>();

    @Column
    private LocalDate endDate;

}
