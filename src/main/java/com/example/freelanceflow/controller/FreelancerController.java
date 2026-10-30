package com.example.freelanceflow.controller;

import com.example.freelanceflow.entity.Freelancer;
import com.example.freelanceflow.service.FreelancerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/freelancers")
public class FreelancerController {

    @Autowired
    private FreelancerService freelancerService;

    @PostMapping("/save")
    public ResponseEntity<String> saveFreelancer(@RequestBody Freelancer freelancer) {
        String message = freelancerService.saveFreelancer(freelancer);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Freelancer>> getAllFreelancers() {
        List<Freelancer> freelancerList = freelancerService.getAllFreelancers();
        return new ResponseEntity<>(freelancerList, HttpStatus.OK);
    }

    @GetMapping("/by-skill")
    public ResponseEntity<List<Freelancer>> getFreelancersBySkill(@RequestParam String name) {
        List<Freelancer> freelancerList = freelancerService.getFreelancersBySkill(name);
        return new ResponseEntity<>(freelancerList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFreelancerById(@PathVariable UUID id) {
        Optional<Freelancer> foundFreelancer = freelancerService.getFreelancerById(id);
        if (foundFreelancer.isPresent()) {
            return new ResponseEntity<>(foundFreelancer.get(), HttpStatus.OK);
        } else {
            String message = "The freelancer with that id does not exist";
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateFreelancer(@PathVariable UUID id, @RequestBody Freelancer freelancer) {
        String message = freelancerService.updateFreelancer(id, freelancer);
        if (message.equals("The freelancer with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFreelancer(@PathVariable UUID id) {
        String message = freelancerService.deleteFreelancer(id);
        if (message.equals("The freelancer with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }
}
