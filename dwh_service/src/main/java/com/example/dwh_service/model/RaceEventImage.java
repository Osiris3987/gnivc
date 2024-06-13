package com.example.dwh_service.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class RaceEventImage {

    private MultipartFile file;
}
