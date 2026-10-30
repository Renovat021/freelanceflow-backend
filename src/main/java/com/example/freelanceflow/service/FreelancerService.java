package com.example.freelanceflow.service;

import com.example.freelanceflow.entity.Freelancer;
import com.example.freelanceflow.entity.Skill;
import com.example.freelanceflow.entity.Workspace;
import com.example.freelanceflow.repository.FreelancerRepository;
import com.example.freelanceflow.repository.SkillRepository;
import com.example.freelanceflow.repository.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FreelancerService {

    @Autowired
    private FreelancerRepository freelancerRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private WorkspaceRepository workspaceRepository;

    public String saveFreelancer(Freelancer freelancer) {
        freelancerRepository.save(freelancer);
        String message = "Freelancer saved successfully";
        return message;
    }

    public List<Freelancer> getAllFreelancers() {
        List<Freelancer> freelancerList = freelancerRepository.findAll();
        return freelancerList;
    }

    public Optional<Freelancer> getFreelancerById(UUID id) {
        Optional<Freelancer> foundFreelancer = freelancerRepository.findById(id);
        return foundFreelancer;
    }

    public List<Freelancer> getFreelancersBySkill(String name) {
        List<Freelancer> freelancerList = freelancerRepository.findFreelancersBySkillName(name);
        return freelancerList;
    }

    public String updateFreelancer(UUID id, Freelancer newFreelancer) {
        String message;
        Optional<Freelancer> foundFreelancer = freelancerRepository.findById(id);
        if (foundFreelancer.isPresent()) {
            Freelancer freelancer = foundFreelancer.get();
            freelancer.setFirstName(newFreelancer.getFirstName());
            freelancer.setLastName(newFreelancer.getLastName());
            freelancer.setEmail(newFreelancer.getEmail());
            freelancer.setHourlyRate(newFreelancer.getHourlyRate());
            freelancer.setWorkspace(newFreelancer.getWorkspace());
            freelancer.setSkills(newFreelancer.getSkills());
            freelancerRepository.save(freelancer);
            message = "Freelancer updated successfully";
        } else {
            message = "The freelancer with that id does not exist";
        }
        return message;
    }

    public String addSkillToFreelancer(UUID freelancerId, UUID skillId) {
        String message;
        Optional<Freelancer> foundFreelancer = freelancerRepository.findById(freelancerId);
        Optional<Skill> foundSkill = skillRepository.findById(skillId);
        if (!foundFreelancer.isPresent()) {
            message = "The freelancer with that id does not exist";
        } else if (!foundSkill.isPresent()) {
            message = "That skill does not exist";
        } else {
            Freelancer freelancer = foundFreelancer.get();
            Skill skill = foundSkill.get();
            List<Skill> skillList = freelancer.getSkills();
            if (skillList.contains(skill)) {
                message = "The freelancer already has that skill";
            } else {
                skillList.add(skill);
                freelancerRepository.save(freelancer);
                message = "Skill added to the freelancer";
            }
        }
        return message;
    }

    public String changeWorkspace(UUID freelancerId, int workspaceNumber) {
        String message;
        Optional<Freelancer> foundFreelancer = freelancerRepository.findById(freelancerId);
        Optional<Workspace> foundWorkspace = workspaceRepository.findByWorkspaceNumber(workspaceNumber);
        if (!foundFreelancer.isPresent()) {
            message = "The freelancer with that id does not exist";
        } else if (!foundWorkspace.isPresent()) {
            message = "The workspace with that workspaceNumber does not exist";
        } else {
            Workspace workspace = foundWorkspace.get();
            if (freelancerRepository.existsByWorkspace(workspace)) {
                message = "That workspace is taken by another freelancer";
            } else {
                Freelancer freelancer = foundFreelancer.get();
                freelancer.setWorkspace(workspace);
                freelancerRepository.save(freelancer);
                message = "Workspace changed successfully";
            }
        }
        return message;
    }

    public String deleteFreelancer(UUID id) {
        String message;
        Optional<Freelancer> foundFreelancer = freelancerRepository.findById(id);
        if (foundFreelancer.isPresent()) {
            freelancerRepository.deleteById(id);
            message = "Freelancer deleted successfully";
        } else {
            message = "The freelancer with that id does not exist";
        }
        return message;
    }
}
