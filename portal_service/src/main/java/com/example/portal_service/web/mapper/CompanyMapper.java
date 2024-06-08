package com.example.portal_service.web.mapper;

import com.example.portal_service.model.company.Company;
import com.example.portal_service.web.dto.company.CompanyResponse;
import com.example.portal_service.web.dto.company.CompanyWithUsersResponse;
import com.example.portal_service.web.dto.dadata_api.DaDataResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CompanyMapper {
    @Mapping(source = "company.id", target = "id")
    public abstract CompanyWithUsersResponse toCompanyWithUsersResponse(Company company);

    @Mapping(source = "company.id", target = "id")
    public abstract CompanyResponse toCompanyResponse(Company company);

    public abstract List<CompanyResponse> toCompanyResponse(List<Company> companies);

    public abstract Company toCompany(DaDataResponse response);
}
