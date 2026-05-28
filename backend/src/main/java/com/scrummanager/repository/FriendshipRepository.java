package com.scrummanager.repository;

import com.scrummanager.domain.model.Friendship;
import com.scrummanager.domain.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    List<Friendship> findByReceiverIdAndStatus(Long receiverId, RequestStatus status);

    @Query("""
            SELECT f FROM Friendship f
            WHERE f.status = 'ACCEPTED'
              AND (f.requestedById = :userId OR f.receiverId = :userId)
            """)
    List<Friendship> findAcceptedFriendships(@Param("userId") Long userId);

    @Query("""
            SELECT f FROM Friendship f
            WHERE f.status = 'ON_HOLD'
              AND ((f.requestedById = :a AND f.receiverId = :b)
                OR (f.requestedById = :b AND f.receiverId = :a))
            """)
    Optional<Friendship> findPendingBetween(@Param("a") Long a, @Param("b") Long b);
}
