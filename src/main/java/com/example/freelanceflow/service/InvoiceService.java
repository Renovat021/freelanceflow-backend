package com.example.freelanceflow.service;

import com.example.freelanceflow.entity.Contract;
import com.example.freelanceflow.entity.ContractStatus;
import com.example.freelanceflow.entity.Invoice;
import com.example.freelanceflow.repository.ContractRepository;
import com.example.freelanceflow.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ContractRepository contractRepository;

    public String saveInvoice(UUID contractId, Invoice invoice) {
        String message;
        Optional<Contract> foundContract = contractRepository.findById(contractId);
        if (foundContract.isPresent()) {
            Contract contract = foundContract.get();
            if (contract.getStatus() == ContractStatus.DELIVERED) {
                LocalDate today = LocalDate.now();
                invoice.setIssuedDate(today);
                invoice.setContract(contract);
                invoiceRepository.save(invoice);
                message = "Invoice saved successfully";
            } else {
                message = "Invoices are only for DELIVERED contracts";
            }
        } else {
            message = "The contract with that id does not exist";
        }
        return message;
    }
}
