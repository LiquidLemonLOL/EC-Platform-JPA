package se.lexicon.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (updatable = false)
    private Long id;

    @Column (nullable = false)
    private Instant orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @NotEmpty(message = "Order must have at least one item")
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    public void addItem(OrderItem item) {
        if (item == null) throw new IllegalArgumentException("Order item cannot be null.");
        if (status != OrderStatus.CREATED) throw new IllegalStateException("Cannot add to order, order status is not active or created");
        items.add(item);
    }

    public void removeItem(OrderItem item) {
        if (item == null) throw new IllegalArgumentException("Order item cannot be null.");
        if (status != OrderStatus.CREATED) throw new IllegalStateException("Cannot remove from order, order status is not active or created");
        items.remove(item);
    }





}
