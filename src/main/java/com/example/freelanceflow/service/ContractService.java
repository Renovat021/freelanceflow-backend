package com.example.freelanceflow.service;

import com.example.freelanceflow.entity.Contract;
import com.example.freelanceflow.entity.ContractStatus;
import com.example.freelanceflow.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ContractService {

    @Autowired
    private ContractRepository contractRepository;

    public String saveContract(Contract contract) {
        contractRepository.save(contract);
        String message = "Contract saved successfully";
        return message;
    }

    public List<Contract> getAllContracts() {
        List<Contract> contractList = contractRepository.findAll();
        return contractList;
    }

    public Optional<Contract> getContractById(UUID id) {
        Optional<Contract> foundContract = contractRepository.findById(id);
        return foundContract;
    }

    public List<Contract> getContractsByStatus(ContractStatus status) {
        List<Contract> contractList = contractRepository.findByStatusOrderByStartDateAsc(status);
        return contractList;
    }

    public List<Contract> getContractsBetween(LocalDate start, LocalDate end) {
        List<Contract> contractList = contractRepository.findByStartDateBetweenOrderByStartDateAsc(start, end);
        return contractList;
    }

    public String updateContract(UUID id, Contract newContract) {
        String message;
        Optional<Contract> foundContract = contractRepository.findById(id);
        if (foundContract.isPresent()) {
            Contract contract = foundContract.get();
            contract.setStartDate(newContract.getStartDate());
            contract.setStatus(newContract.getStatus());
            contract.setTitle(newContract.getTitle());
            contract.setBudget(newContract.getBudget());
            contract.setClient(newContract.getClient());
            contract.setFreelancer(newContract.getFreelancer());
            contractRepository.save(contract);
            message = "Contract updated successfully";
        } else {
            message = "The contract with that id does not exist";
        }
        return message;
    }

    public String deleteContract(UUID id) {
        String message;
        Optional<Contract> foundContract = contractRepository.findById(id);
        if (foundContract.isPresent()) {
            contractRepository.deleteById(id);
            message = "Contract deleted successfully";
        } else {
            message = "The contract with that id does not exist";
        }
        return message;
    }
}
