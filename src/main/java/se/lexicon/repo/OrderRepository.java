package se.lexicon.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.lexicon.entity.Order;
import se.lexicon.entity.OrderStatus;

import java.time.Instant;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByCustomerId(Long customerId);

    // SELECT o.*, oi.* FROM orders o JOIN order_items oi ON oi.order_id = o.id WHERE o.status = ?
    @Query("SELECT DISTINCT o FROM Order o JOIN FETCH o.items WHERE o.status = :status")
    List<Order> findByStatusWithItems(@Param("status") OrderStatus orderStatus);

    List<Order> findByOrderDateAfter(Instant date);

    List<Order> findByOrderDateBetween(Instant start, Instant end);

    @Query("SELECT DISTINCT o FROM Order o JOIN FETCH o.items oi WHERE oi.product.name = :productName")
    List<Order> findOrdersContainingProductName(@Param("productName") String productName);

    long countByStatus(OrderStatus orderStatus);

    List<Order> findByCustomerIdAndStatus(Long customerId, OrderStatus orderStatus);

}
