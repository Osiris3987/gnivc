package com.example.portal_service.web.controller;

import com.example.portal_service.model.company.Company;
import com.example.portal_service.model.transport.Transport;
import com.example.portal_service.service.CompanyService;
import com.example.portal_service.service.TransportService;
import com.example.portal_service.web.dto.transport.TransportRequest;
import com.example.portal_service.web.dto.transport.TransportResponse;
import com.example.portal_service.web.mapper.TransportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/transports")
@RequiredArgsConstructor
public class TransportController {
    private final TransportService transportService;
    private final TransportMapper transportMapper;
    private final CompanyService companyService;

    @PostMapping
    public TransportResponse createTransport(@RequestBody TransportRequest request) {
        Transport transport = transportService.create(transportMapper.toEntity(request), request.getCompanyId());
        return transportMapper.toResponseDto(transport);
    }

    @PostMapping("/getByVinAndCompany")
    public TransportResponse getByVinAndCompany(@RequestBody TransportRequest request) {
        Company company = companyService.findById(request.getCompanyId());
        return transportMapper.toResponseDto(transportService.findByVinAndCompany(request.getVin(), company));
    }
}
