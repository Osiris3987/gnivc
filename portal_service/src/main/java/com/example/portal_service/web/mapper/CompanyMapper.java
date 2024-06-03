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

    //@Mapping(source = "company.id", target = "id")
    public abstract CompanyResponse toCompanyResponse(Company company);

    public abstract List<CompanyResponse> toCompanyResponse(List<Company> companies);
    @Mapping(source = "response.value", target = "name")
    @Mapping(source = "response.data.inn", target = "inn")
    @Mapping(source = "response.data.ogrn", target = "ogrn")
    @Mapping(source = "response.data.kpp", target = "kpp")
    @Mapping(source = "response.data.address.value", target = "address")
    public abstract Company toCompany(DaDataResponse response);
}
