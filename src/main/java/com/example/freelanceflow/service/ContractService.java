package com.example.freelanceflow.service;

import com.example.freelanceflow.entity.Contract;
import com.example.freelanceflow.entity.ContractStatus;
import com.example.freelanceflow.entity.Freelancer;
import com.example.freelanceflow.repository.ContractRepository;
import com.example.freelanceflow.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ContractService {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    public String saveContract(Contract contract) {
        String message;
        LocalDate today = LocalDate.now();
        LocalDate startDate = contract.getStartDate();
        if (startDate.isBefore(today)) {
            message = "Contract date cannot be in the past";
        } else {
            Freelancer freelancer = contract.getFreelancer();
            boolean alreadyHasContract = contractRepository.existsByFreelancerAndStartDateAndStatusNot(freelancer, startDate, ContractStatus.CANCELLED);
            if (alreadyHasContract) {
                message = "Freelancer already has a contract starting on that date";
            } else {
                contract.setStatus(ContractStatus.PROPOSED);
                contractRepository.save(contract);
                message = "Contract saved successfully";
            }
        }
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

    public List<Object[]> getContractStats() {
        List<Object[]> statsList = contractRepository.countContractsByStatus();
        return statsList;
    }

    public String updateContract(UUID id, Contract newContract) {
        String message;
        Optional<Contract> foundContract = contractRepository.findById(id);
        if (foundContract.isPresent()) {
            Contract contract = foundContract.get();
            contract.setStartDate(newContract.getStartDate());
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

    public String updateStatus(UUID id, ContractStatus newStatus) {
        String message;
        Optional<Contract> foundContract = contractRepository.findById(id);
        if (foundContract.isPresent()) {
            Contract contract = foundContract.get();
            ContractStatus currentStatus = contract.getStatus();
            boolean allowed = false;
            if (currentStatus == ContractStatus.PROPOSED) {
                if (newStatus == ContractStatus.ACTIVE || newStatus == ContractStatus.CANCELLED) {
                    allowed = true;
                }
            } else if (currentStatus == ContractStatus.ACTIVE) {
                if (newStatus == ContractStatus.DELIVERED || newStatus == ContractStatus.CANCELLED) {
                    allowed = true;
                }
            }
            if (allowed) {
                contract.setStatus(newStatus);
                contractRepository.save(contract);
                message = "Contract status updated successfully";
            } else {
                message = "Cannot change status from " + currentStatus + " to " + newStatus;
            }
        } else {
            message = "The contract with that id does not exist";
        }
        return message;
    }

    @Transactional
    public String cancelContractsOfDay(UUID freelancerId, LocalDate date) {
        int result = contractRepository.cancelContractsOfDay(freelancerId, date);
        String message = result + " contracts cancelled";
        return message;
    }

    public String deleteContract(UUID id) {
        String message;
        Optional<Contract> foundContract = contractRepository.findById(id);
        if (foundContract.isPresent()) {
            Contract contract = foundContract.get();
            if (invoiceRepository.existsByContract(contract)) {
                message = "That contract has invoices, delete them first";
            } else {
                contractRepository.deleteById(id);
                message = "Contract deleted successfully";
            }
        } else {
            message = "The contract with that id does not exist";
        }
        return message;
    }
}
