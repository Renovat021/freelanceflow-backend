package com.example.freelanceflow.controller;

import com.example.freelanceflow.entity.Contract;
import com.example.freelanceflow.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Contract>> getAllContracts() {
        List<Contract> contractList = contractService.getAllContracts();
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
