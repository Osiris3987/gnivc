package com.example.driver_service.web.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Getter
@Setter
public class RaceEventImage implements Serializable {

    private MultipartFile file;
}
