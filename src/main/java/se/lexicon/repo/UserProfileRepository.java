package se.lexicon.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.lexicon.entity.UserProfile;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    Optional<UserProfile> findByNickname(@Param("nickname") String nickname);

    @Query("SELECT up FROM UserProfile up WHERE up.phoneNumber LIKE %:key%")
    List<UserProfile> findByPhoneNumberContaining(@Param("key") String key);

    @Query("SELECT up FROM UserProfile up WHERE up.bio IS NOT NULL")
    List<UserProfile> findNotNullBios();

    @Query("SELECT up FROM UserProfile up WHERE up.nickname LIKE :key%")
    List<UserProfile> findByNicknameStartingWith(@Param("key") String key);

    List<UserProfile> findByCreatedAtAfter(@Param("date") Instant date);

    @Query("SELECT COUNT(up) FROM UserProfile up WHERE up.phoneNumber LIKE :key%")
    long countByPhoneNumberStartingWith(@Param("key") String key);






}
