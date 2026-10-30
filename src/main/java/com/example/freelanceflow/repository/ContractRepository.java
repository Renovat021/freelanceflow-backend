package com.example.freelanceflow.repository;

import com.example.freelanceflow.entity.Contract;
import com.example.freelanceflow.entity.ContractStatus;
import com.example.freelanceflow.entity.Freelancer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ContractRepository extends JpaRepository<Contract, UUID> {

    List<Contract> findByStatusOrderByStartDateAsc(ContractStatus status);

    List<Contract> findByStartDateBetweenOrderByStartDateAsc(LocalDate start, LocalDate end);

    boolean existsByFreelancerAndStartDateAndStatusNot(Freelancer freelancer, LocalDate startDate, ContractStatus status);
}
