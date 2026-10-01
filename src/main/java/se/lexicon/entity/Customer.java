package se.lexicon.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString

@Entity
@Table(name = "customers")
public class Customer {


    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false, length = 100)
    private String firstName;

    @Column (nullable = false, length = 100)
    private String lastName;

    @Column (unique = true, nullable = false, length = 150)
    private String email;

    @Column (length = 120, nullable = false)
    private String password;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "profile_id", unique = true)
    @ToString.Exclude
    private UserProfile profile;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Order> orders = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
    }

    public Customer(String firstName, String lastName, String email, String password, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.address = address;
    }

}
