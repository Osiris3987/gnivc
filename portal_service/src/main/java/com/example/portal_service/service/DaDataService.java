package com.example.portal_service.service;

import com.example.portal_service.web.dto.dadata_api.DaDataJsomArrayResponse;
import com.example.portal_service.web.dto.dadata_api.DaDataRequest;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class DaDataService {
    private final RestTemplate restTemplate;
    private final Gson gson = new Gson();

    public DaDataJsomArrayResponse sendPostRequest(String url, DaDataRequest dto) {

        RequestEntity<String> requestEntity = RequestEntity
                .post(URI.create(url))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Token 355d7bf9b6e3558691d85bc43a8ba9f5db1f3922")
                .body(gson.toJson(dto));
        String json = restTemplate.exchange(requestEntity, String.class).getBody();
        return gson.fromJson(json, DaDataJsomArrayResponse.class);
    }
}
