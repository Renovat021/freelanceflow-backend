package com.example.freelanceflow.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity 
@Table(name = "workspace")
public class Workspace {

    @Id 
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    
    private int workspaceNumber;

    private String name;

    public Workspace() {

    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public int getWorkspaceNumber() {
        return workspaceNumber;
    }
    public void setWorkspaceNumber(int workspaceNumber) {
        this.workspaceNumber = workspaceNumber;
    }
    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    
}
