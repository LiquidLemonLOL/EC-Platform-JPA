package se.lexicon.entity;

import jakarta.persistence.*;
import lombok.*;
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
    private int id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    LocalDate startDate;

    @ManyToMany(mappedBy = "promotions", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Product> products = new HashSet<>();

    @Column
    LocalDate endDate;

}
