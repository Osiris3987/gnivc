package com.example.portal_service.repository;

import com.example.portal_service.model.company.Company;
import com.example.portal_service.model.transport.Transport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TransportRepository extends JpaRepository<Transport, UUID> {
    Optional<Transport> findByVinAndCompany(String vin, Company company);
}
