package com.example.freelanceflow.controller;

import com.example.freelanceflow.entity.Client;
import com.example.freelanceflow.entity.Freelancer;
import com.example.freelanceflow.service.ClientService;
import com.example.freelanceflow.service.FreelancerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private FreelancerService freelancerService;

    @PostMapping("/save")
    public ResponseEntity<String> saveClient(@RequestBody Client client) {
        String message = clientService.saveClient(client);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clientList = clientService.getAllClients();
        return new ResponseEntity<>(clientList, HttpStatus.OK);
    }

    @GetMapping("/by-last-name")
    public ResponseEntity<List<Client>> getClientsByLastName(@RequestParam String lastName) {
        List<Client> clientList = clientService.getClientsByLastName(lastName);
        return new ResponseEntity<>(clientList, HttpStatus.OK);
    }

    @GetMapping("/of-freelancer/{freelancerId}")
    public ResponseEntity<?> getClientsOfFreelancer(@PathVariable UUID freelancerId) {
        Optional<Freelancer> foundFreelancer = freelancerService.getFreelancerById(freelancerId);
        if (foundFreelancer.isPresent()) {
            List<Client> clientList = clientService.getClientsOfFreelancer(freelancerId);
            return new ResponseEntity<>(clientList, HttpStatus.OK);
        } else {
            String message = "The freelancer with that id does not exist";
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClientById(@PathVariable UUID id) {
        Optional<Client> foundClient = clientService.getClientById(id);
        if (foundClient.isPresent()) {
            return new ResponseEntity<>(foundClient.get(), HttpStatus.OK);
        } else {
            String message = "The client with that id does not exist";
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateClient(@PathVariable UUID id, @RequestBody Client client) {
        String message = clientService.updateClient(id, client);
        if (message.equals("The client with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable UUID id) {
        String message = clientService.deleteClient(id);
        if (message.equals("The client with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }
}
