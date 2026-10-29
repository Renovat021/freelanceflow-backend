package com.example.freelanceflow.service;

import com.example.freelanceflow.entity.Workspace;
import com.example.freelanceflow.repository.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class WorkspaceService {

    @Autowired
    private WorkspaceRepository workspaceRepository;

    public String saveWorkspace(Workspace workspace) {
        workspaceRepository.save(workspace);
        return "Workspace saved successfully";
    }

    public Optional<Workspace> getWorkspaceById(UUID id) {
        return workspaceRepository.findById(id);
    }
}