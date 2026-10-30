package com.example.freelanceflow.repository;

import com.example.freelanceflow.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface WorkspaceRepository extends JpaRepository<Workspace, UUID> {

    @Query("SELECT w FROM Workspace w WHERE NOT EXISTS (SELECT f FROM Freelancer f WHERE f.workspace = w) ORDER BY w.workspaceNumber")
    List<Workspace> findFreeWorkspaces();
}
