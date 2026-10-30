package com.example.freelanceflow.repository;

import com.example.freelanceflow.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {

    List<Client> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);
}
