package se.lexicon.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.lexicon.entity.Promotion;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PromotionRepository extends JpaRepository<Promotion, Integer> {

    @Query("SELECT pro FROM Promotion pro WHERE pro.startDate <= :date AND (pro.endDate IS NULL OR pro.endDate >= :date)")
    List<Promotion> findActiveOnDate(@Param("date") LocalDate date);

    Optional<Promotion> findByCode(String code);

    List<Promotion> findByStartDateAfter(LocalDate date);

    List<Promotion> findByEndDateBefore(LocalDate date);

    List<Promotion> findByEndDateIsNull();

    default List<Promotion> findActiveNow() {
        return findActiveOnDate(LocalDate.now());
    }

}
