package com.example.freelanceflow.service;

import com.example.freelanceflow.entity.Freelancer;
import com.example.freelanceflow.repository.FreelancerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FreelancerService {

    @Autowired
    private FreelancerRepository freelancerRepository;

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
