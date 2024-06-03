package com.example.custom_spring_boot_starter;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UserContext {
    private List<String> roles;
    private UUID userId;
}
