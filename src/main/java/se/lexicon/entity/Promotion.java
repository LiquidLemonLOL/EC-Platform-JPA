package se.lexicon.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
@Table(name = "promotions")
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    LocalDate startDate;

    @Column
    LocalDate endDate;

}
