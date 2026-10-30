package com.example.freelanceflow.repository;

import com.example.freelanceflow.entity.Freelancer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface FreelancerRepository extends JpaRepository<Freelancer, UUID> {

    @Query("SELECT DISTINCT f FROM Freelancer f JOIN f.skills s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Freelancer> findFreelancersBySkillName(@Param("name") String name);
}
