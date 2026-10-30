package com.example.freelanceflow.controller;

import com.example.freelanceflow.entity.Invoice;
import com.example.freelanceflow.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/save")
    public ResponseEntity<String> saveInvoice(@RequestParam UUID contractId, @RequestBody Invoice invoice) {
        String message = invoiceService.saveInvoice(contractId, invoice);
        if (message.equals("The contract with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        } else if (message.equals("Invoices are only for DELIVERED contracts")) {
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
    }

    @GetMapping("/client/{email}")
    public ResponseEntity<List<Invoice>> getInvoicesOfClient(@PathVariable String email) {
        List<Invoice> invoiceList = invoiceService.getInvoicesOfClient(email);
        return new ResponseEntity<>(invoiceList, HttpStatus.OK);
    }
}
