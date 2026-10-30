package com.example.freelanceflow.controller;

import com.example.freelanceflow.entity.Workspace;
import com.example.freelanceflow.service.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/workspaces")
public class WorkspaceController {

    @Autowired
    private WorkspaceService workspaceService;

    @PostMapping("/save")
    public ResponseEntity<String> saveWorkspace(@RequestBody Workspace workspace) {
        String message = workspaceService.saveWorkspace(workspace);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Workspace>> getAllWorkspaces() {
        List<Workspace> workspaceList = workspaceService.getAllWorkspaces();
        return new ResponseEntity<>(workspaceList, HttpStatus.OK);
    }

    @GetMapping("/free")
    public ResponseEntity<List<Workspace>> getFreeWorkspaces() {
        List<Workspace> workspaceList = workspaceService.getFreeWorkspaces();
        return new ResponseEntity<>(workspaceList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getWorkspaceById(@PathVariable UUID id) {
        Optional<Workspace> foundWorkspace = workspaceService.getWorkspaceById(id);
        if (foundWorkspace.isPresent()) {
            return new ResponseEntity<>(foundWorkspace.get(), HttpStatus.OK);
        } else {
            String message = "The workspace with that id does not exist";
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateWorkspace(@PathVariable UUID id, @RequestBody Workspace workspace) {
        String message = workspaceService.updateWorkspace(id, workspace);
        if (message.equals("The workspace with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteWorkspace(@PathVariable UUID id) {
        String message = workspaceService.deleteWorkspace(id);
        if (message.equals("The workspace with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }
}
