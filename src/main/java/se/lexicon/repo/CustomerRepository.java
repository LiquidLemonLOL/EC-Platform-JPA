package se.lexicon.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.lexicon.entity.Customer;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // SELECT * FROM customer WHERE email = ?
    Optional<Customer> findByEmail(String email);

    List<Customer> findByLastName(String lastName);

    List<Customer> findByAddressCity(String city);

    boolean existsByEmail(String email);

    @Query("SELECT c FROM Customer c WHERE c.email LIKE %:key%")
    List<Customer> selectByEmailContaining(@Param("key") String key);

    @Query("SELECT c FROM Customer c WHERE c.createdAt > :date")
    List<Customer> selectByCreatedAtAfter(@Param("date") Instant date);

    @Query("SELECT c FROM Customer c WHERE c.createdAt BETWEEN :dateAfter AND :dateBefore")
    List<Customer> selectByCreatedAtBetween(@Param("dateAfter") Instant dateAfter, @Param("dateBefore") Instant dateBefore);

    @Query("SELECT COUNT(c) FROM Customer c WHERE c.address.city = :city")
    long countCustomersByCity(@Param("city") String city);


}
