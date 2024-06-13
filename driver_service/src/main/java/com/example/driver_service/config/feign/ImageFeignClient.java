package com.example.driver_service.config.feign;

import com.example.driver_service.web.dto.RaceEventImage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "images", url = "http://localhost:8085/api/v1/images")
public interface ImageFeignClient {
    @PostMapping(value = "/raceEvents", consumes = "multipart/form-data")
    String createRaceEventImage(RaceEventImage image);
}
