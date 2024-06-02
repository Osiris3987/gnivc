package com.example.portal_service.web.controller;

import com.example.gnivc_spring_boot_starter.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/test")
public class TestController {
    @Qualifier()
    private final UserContext userContext;

    @GetMapping
    public String getUserContext(){
        return userContext.getRoles().toString();
    }
}
