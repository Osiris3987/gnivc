package com.example.gateway_service_gnivc.web.controller;

import com.example.gateway_service_gnivc.security.model.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserDetailsTestController {
    @GetMapping("/me")
    public List<String> getRoles(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userDetails.getRoles();
    }
}
