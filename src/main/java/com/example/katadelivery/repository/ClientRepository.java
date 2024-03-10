package com.example.katadelivery.repository;

import com.example.katadelivery.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, String> {
    Boolean existsByEmail(String email);
    Optional<Client> findByEmail(String email);
}
