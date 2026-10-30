package com.example.freelanceflow.service;

import com.example.freelanceflow.entity.Contract;
import com.example.freelanceflow.entity.ContractStatus;
import com.example.freelanceflow.entity.Invoice;
import com.example.freelanceflow.repository.ContractRepository;
import com.example.freelanceflow.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
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

    public List<Invoice> getInvoicesOfClient(String email) {
        List<Invoice> invoiceList = invoiceRepository.findInvoicesOfClient(email);
        return invoiceList;
    }

    public String deleteInvoice(UUID id) {
        String message;
        Optional<Invoice> foundInvoice = invoiceRepository.findById(id);
        if (foundInvoice.isPresent()) {
            invoiceRepository.deleteById(id);
            message = "Invoice deleted successfully";
        } else {
            message = "The invoice with that id does not exist";
        }
        return message;
    }
}
