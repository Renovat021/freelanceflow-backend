package com.example.freelanceflow.service;

import com.example.freelanceflow.entity.Client;
import com.example.freelanceflow.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public String saveClient(Client client) {
        clientRepository.save(client);
        String message = "Client saved successfully";
        return message;
    }

    public List<Client> getAllClients() {
        List<Client> clientList = clientRepository.findAll();
        return clientList;
    }

    public Optional<Client> getClientById(UUID id) {
        Optional<Client> foundClient = clientRepository.findById(id);
        return foundClient;
    }

    public String updateClient(UUID id, Client newClient) {
        String message;
        Optional<Client> foundClient = clientRepository.findById(id);
        if (foundClient.isPresent()) {
            Client client = foundClient.get();
            client.setFirstName(newClient.getFirstName());
            client.setLastName(newClient.getLastName());
            client.setEmail(newClient.getEmail());
            client.setCompany(newClient.getCompany());
            clientRepository.save(client);
            message = "Client updated successfully";
        } else {
            message = "The client with that id does not exist";
        }
        return message;
    }

    public String deleteClient(UUID id) {
        String message;
        Optional<Client> foundClient = clientRepository.findById(id);
        if (foundClient.isPresent()) {
            clientRepository.deleteById(id);
            message = "Client deleted successfully";
        } else {
            message = "The client with that id does not exist";
        }
        return message;
    }
}
