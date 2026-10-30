package com.example.freelanceflow.repository;

import com.example.freelanceflow.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface SkillRepository extends JpaRepository<Skill, UUID> {

    @Query("SELECT s FROM Skill s WHERE s.freelancers IS EMPTY")
    List<Skill> findUnusedSkills();
}
