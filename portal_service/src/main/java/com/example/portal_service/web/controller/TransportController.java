package com.example.portal_service.web.controller;

import com.example.portal_service.model.transport.Transport;
import com.example.portal_service.service.TransportService;
import com.example.portal_service.web.dto.transport.TransportRequest;
import com.example.portal_service.web.dto.transport.TransportResponse;
import com.example.portal_service.web.mapper.TransportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transports")
@RequiredArgsConstructor
public class TransportController {
    private final TransportService transportService;
    private final TransportMapper transportMapper;

    @PostMapping
    public TransportResponse createTransport(@RequestBody TransportRequest request) {
        Transport transport = transportService.create(transportMapper.toEntity(request), request.getCompanyId());
        return transportMapper.toResponseDto(transport);
    }
}
