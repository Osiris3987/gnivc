package com.example.portal_service.web.mapper;

import com.example.portal_service.model.transport.Transport;
import com.example.portal_service.web.dto.transport.TransportRequest;
import com.example.portal_service.web.dto.transport.TransportResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransportMapper {

    @Mapping(source = "request.vin", target = "vin")
    @Mapping(source = "request.year", target = "year")
    @Mapping(source = "request.stateNumber", target = "stateNumber")
    Transport toEntity(TransportRequest request);

    @Mapping(source = "transport.company.id", target = "companyId")
    TransportResponse toResponseDto(Transport transport);
}
