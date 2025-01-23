package com.saeid.repository;

import com.saeid.Entity.Token;
import com.saeid.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);
    Optional<Token> findByRefreshToken(String refreshToken);
    boolean existsByToken(String token);
    void deleteByUser(User user);
    @Query("SELECT COUNT(t) FROM Token t WHERE t.user = :user AND t.revoked = false AND t.expiresAt > CURRENT_TIMESTAMP")
    long countActiveTokensByUser(@Param("user") User user);
}
