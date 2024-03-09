package com.example.katadelivery.repository;

import com.example.katadelivery.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, String> {
    Boolean existsByEmail(String email);
}
