package com.example.portal_service.service;

import com.example.portal_service.model.company.Company;
import com.example.portal_service.model.company.GenericCompanyRole;
import com.example.portal_service.model.exception.ResourceNotFoundException;
import com.example.portal_service.model.transport.Transport;
import com.example.portal_service.repository.TransportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransportService {

    private final TransportRepository transportRepository;

    private final CompanyService companyService;

    private final KeycloakService keycloakService;

    public Transport create(Transport transport, UUID companyId) {
        Company company = companyService.findById(companyId);
        keycloakService.userHasCurrentRole(
                List.of(
                        GenericCompanyRole.ADMIN_.name() + company.getName(),
                        GenericCompanyRole.LOGIST_.name() + company.getName()
                )
        );
        transport.setCompany(company);
        transportRepository.save(transport);
        return transport;
    }

    public Transport findByVinAndCompany(String vin, Company company) {
        return transportRepository.findByVinAndCompany(vin, company)
                .orElseThrow(() -> new ResourceNotFoundException("Transport not found"));
    }
}
