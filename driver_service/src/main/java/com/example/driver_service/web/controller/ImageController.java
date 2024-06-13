package com.example.driver_service.web.controller;

import com.example.driver_service.config.feign.ImageFeignClient;
import com.example.driver_service.web.dto.RaceEventImage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageFeignClient imageClient;

    @PostMapping
    public String createImage(RaceEventImage image) {
        return imageClient.createRaceEventImage(image);
    }
}
