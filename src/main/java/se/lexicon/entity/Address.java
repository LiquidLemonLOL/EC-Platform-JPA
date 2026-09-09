package se.lexicon.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode

@Entity
@Table(name = "addresses")
public class Address {


    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (updatable = false)
    private Long id;

    @Column (nullable = false, length = 100)
    private String street;

    @Column (nullable = false, length = 100)
    private String city;

    @Column (nullable = false, length = 10)
    private String zipCode;

}
