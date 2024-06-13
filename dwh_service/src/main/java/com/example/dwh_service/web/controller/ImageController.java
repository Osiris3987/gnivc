package com.example.dwh_service.web.controller;

import com.example.dwh_service.model.RaceEventImage;
import com.example.dwh_service.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/raceEvents")
    public String createRaceEventImage(RaceEventImage image) {
        return imageService.upload(image);
    }
}
