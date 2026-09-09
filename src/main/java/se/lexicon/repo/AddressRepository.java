package se.lexicon.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.lexicon.entity.Address;

import java.util.List;

public interface AddressRepository  extends JpaRepository<Address, Long> {

    List<Address> findByZipCode(String zipCode);

    List<Address> findByCity(String city);

    List<Address> findAddressesByStreet(String street);

    @Query("SELECT COUNT(a) FROM Address a WHERE a.zipCode = :zipCode")
    long countAddressesByZipCode(@Param("zipCode") String zipCode);

    @Query("SELECT a FROM Address a WHERE a.zipCode LIKE :zipCode%")
    List<Address> findAddressesByZipCodeStartingWith(@Param("zipCode") String zipCode);


}

