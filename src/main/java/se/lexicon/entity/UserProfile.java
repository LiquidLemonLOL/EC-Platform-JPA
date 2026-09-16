package se.lexicon.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")

@Entity
@Table(name = "user_profiles")
public class UserProfile {


    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (length = 100, nullable = false)
    private String nickname;

    @Column (length = 100, nullable = false)
    private String phoneNumber;

    @Column (length = 500)
    private String bio;

    @OneToOne(mappedBy = "profile", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Customer customer;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
    }

}
