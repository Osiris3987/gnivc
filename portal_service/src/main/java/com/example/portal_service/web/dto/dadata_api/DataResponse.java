package com.example.portal_service.web.dto.dadata_api;

import lombok.Data;

@Data
public class DataResponse {
    private String kpp;
    private String ogrn;
    private AddressResponse address;
    private String inn;
}
