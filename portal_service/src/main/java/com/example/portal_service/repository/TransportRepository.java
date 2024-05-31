package com.example.portal_service.repository;

import com.example.portal_service.model.transport.Transport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransportRepository extends JpaRepository<Transport, UUID> {
}
