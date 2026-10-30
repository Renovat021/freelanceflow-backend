package com.example.freelanceflow.repository;

import com.example.freelanceflow.entity.Freelancer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FreelancerRepository extends JpaRepository<Freelancer, UUID> {
}
