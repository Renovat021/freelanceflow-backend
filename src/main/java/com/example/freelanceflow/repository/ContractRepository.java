package com.example.freelanceflow.repository;

import com.example.freelanceflow.entity.Contract;
import com.example.freelanceflow.entity.ContractStatus;
import com.example.freelanceflow.entity.Freelancer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ContractRepository extends JpaRepository<Contract, UUID> {

    List<Contract> findByStatusOrderByStartDateAsc(ContractStatus status);

    List<Contract> findByStartDateBetweenOrderByStartDateAsc(LocalDate start, LocalDate end);

    boolean existsByFreelancerAndStartDateAndStatusNot(Freelancer freelancer, LocalDate startDate, ContractStatus status);

    @Query("SELECT c.status, COUNT(c) FROM Contract c GROUP BY c.status")
    List<Object[]> countContractsByStatus();

    @Modifying
    @Query("UPDATE Contract c SET c.status = com.example.freelanceflow.entity.ContractStatus.CANCELLED WHERE c.freelancer.id = :freelancerId AND c.startDate = :date AND c.status <> com.example.freelanceflow.entity.ContractStatus.DELIVERED")
    int cancelContractsOfDay(@Param("freelancerId") UUID freelancerId, @Param("date") LocalDate date);
}
