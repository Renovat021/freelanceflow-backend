package com.example.freelanceflow.service;

import com.example.freelanceflow.entity.Workspace;
import com.example.freelanceflow.repository.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WorkspaceService {

    @Autowired
    private WorkspaceRepository workspaceRepository;

    public String saveWorkspace(Workspace workspace) {
        workspaceRepository.save(workspace);
        String message = "Workspace saved successfully";
        return message;
    }

    public List<Workspace> getAllWorkspaces() {
        List<Workspace> workspaceList = workspaceRepository.findAll();
        return workspaceList;
    }

    public Optional<Workspace> getWorkspaceById(UUID id) {
        Optional<Workspace> foundWorkspace = workspaceRepository.findById(id);
        return foundWorkspace;
    }

    public List<Workspace> getFreeWorkspaces() {
        List<Workspace> workspaceList = workspaceRepository.findFreeWorkspaces();
        return workspaceList;
    }

    public String updateWorkspace(UUID id, Workspace newWorkspace) {
        String message;
        Optional<Workspace> foundWorkspace = workspaceRepository.findById(id);
        if (foundWorkspace.isPresent()) {
            Workspace workspace = foundWorkspace.get();
            workspace.setWorkspaceNumber(newWorkspace.getWorkspaceNumber());
            workspace.setName(newWorkspace.getName());
            workspaceRepository.save(workspace);
            message = "Workspace updated successfully";
        } else {
            message = "The workspace with that id does not exist";
        }
        return message;
    }

    public String deleteWorkspace(UUID id) {
        String message;
        Optional<Workspace> foundWorkspace = workspaceRepository.findById(id);
        if (foundWorkspace.isPresent()) {
            workspaceRepository.deleteById(id);
            message = "Workspace deleted successfully";
        } else {
            message = "The workspace with that id does not exist";
        }
        return message;
    }
}
