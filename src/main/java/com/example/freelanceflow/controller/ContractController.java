package com.example.freelanceflow.controller;

import com.example.freelanceflow.entity.Contract;
import com.example.freelanceflow.entity.ContractStatus;
import com.example.freelanceflow.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    @Autowired
    private ContractService contractService;

    @PostMapping("/save")
    public ResponseEntity<String> saveContract(@RequestBody Contract contract) {
        String message = contractService.saveContract(contract);
        if (message.equals("Contract date cannot be in the past")) {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        } else if (message.equals("Freelancer already has a contract starting on that date")) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Contract>> getAllContracts() {
        List<Contract> contractList = contractService.getAllContracts();
        return new ResponseEntity<>(contractList, HttpStatus.OK);
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<Contract>> getContractsByStatus(@RequestParam ContractStatus status) {
        List<Contract> contractList = contractService.getContractsByStatus(status);
        return new ResponseEntity<>(contractList, HttpStatus.OK);
    }

    @GetMapping("/between")
    public ResponseEntity<List<Contract>> getContractsBetween(@RequestParam String start, @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        List<Contract> contractList = contractService.getContractsBetween(startDate, endDate);
        return new ResponseEntity<>(contractList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getContractById(@PathVariable UUID id) {
        Optional<Contract> foundContract = contractService.getContractById(id);
        if (foundContract.isPresent()) {
            return new ResponseEntity<>(foundContract.get(), HttpStatus.OK);
        } else {
            String message = "The contract with that id does not exist";
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateContract(@PathVariable UUID id, @RequestBody Contract contract) {
        String message = contractService.updateContract(id, contract);
        if (message.equals("The contract with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable UUID id, @RequestParam ContractStatus status) {
        String message = contractService.updateStatus(id, status);
        if (message.equals("The contract with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else if (message.equals("Contract status updated successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteContract(@PathVariable UUID id) {
        String message = contractService.deleteContract(id);
        if (message.equals("The contract with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }
}
