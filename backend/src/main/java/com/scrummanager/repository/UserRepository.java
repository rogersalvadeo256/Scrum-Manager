package com.scrummanager.repository;

import com.scrummanager.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameOrEmail(String username, String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    @Query("""
            select u from User u
            where lower(u.profile.name) like lower(concat('%', :name, '%'))
               or lower(u.username) like lower(concat('%', :name, '%'))
            """)
    List<User> searchByNameOrUsername(@Param("name") String name);

    @Query("select u from User u where u.accountLockedUntil is not null and u.accountLockedUntil <= :now")
    List<User> findUsersReadyToUnlock(@Param("now") LocalDateTime now);

    @Query("""
            select u from User u
            where u.passwordExpiresAt is not null
              and u.passwordExpiresAt between :start and :end
            """)
    List<User> findUsersWithPasswordExpiryBetween(@Param("start") LocalDateTime start,
                                                  @Param("end") LocalDateTime end);
}
