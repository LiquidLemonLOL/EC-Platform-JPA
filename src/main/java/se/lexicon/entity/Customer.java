package se.lexicon.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode

@Entity
@Table(name = "customers")
public class Customer {


    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (updatable = false)
    private Long id;

    @Column (nullable = false, length = 100)
    private String firstName;

    @Column (nullable = false, length = 100)
    private String lastName;

    @Column (unique = true, nullable = false, length = 150)
    private String email;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    private Address address;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "profile_id", unique = true)
    private UserProfile profile;

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
    }

}
