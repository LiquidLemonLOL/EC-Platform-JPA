package se.lexicon.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import se.lexicon.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomerId(Long customerId);

}
