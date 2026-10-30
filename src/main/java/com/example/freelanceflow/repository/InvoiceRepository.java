package com.example.freelanceflow.repository;

import com.example.freelanceflow.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {

    @Query("SELECT d FROM Invoice d WHERE d.contract.client.email = :email ORDER BY d.issuedDate DESC")
    List<Invoice> findInvoicesOfClient(@Param("email") String email);
}
