package com.example.freelanceflow.repository;

import com.example.freelanceflow.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {

    List<Client> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    @Query("SELECT DISTINCT c.client FROM Contract c WHERE c.freelancer.id = :freelancerId")
    List<Client> findClientsOfFreelancer(@Param("freelancerId") UUID freelancerId);
}
