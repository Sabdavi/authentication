package com.saeid.repository;

import com.saeid.Entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByClientId(String clientId);
    Optional<Client> findByClientIdAndClientSecret(String clientId, String clientSecret);
    Optional<Client> findByName(String clientName);
    boolean existsByClientId(String clientId);
}
