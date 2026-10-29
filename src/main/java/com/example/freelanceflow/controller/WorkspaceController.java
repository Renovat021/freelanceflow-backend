package com.example.freelanceflow.controller;

import com.example.freelanceflow.entity.Workspace;
import com.example.freelanceflow.service.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/workspaces")
public class WorkspaceController {

    @Autowired
    private WorkspaceService workspaceService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Workspace workspace) {
        String message = workspaceService.saveWorkspace(workspace);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Workspace> workspace = workspaceService.getWorkspaceById(id);
        if (workspace.isPresent()) {
            return ResponseEntity.ok(workspace.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The workspace with that id does not exist");
        }
    }
}