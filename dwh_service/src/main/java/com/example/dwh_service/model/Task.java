package com.example.dwh_service.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Task {
    private UUID id;

    private String start_point;

    private String end_point;

    private String driver_first_name;

    private String driver_last_name;

    private String description;

    private String transport_state_number;

    private String company_id;

    private UUID driver_id;
}
/*
    "payload": {
    "id": "8cab67e1-7e21-42ba-9608-d23e0acb5368",
    "start_point": "228",
    "end_point": "337",
    "driver_first_name": "Oleg",
    "driver_last_name": "Driver",
    "description": "production code",
    "transport_state_number": "x100xx",
    "company_id": "b24842d4-91ae-463b-ac83-50179208d04e",
    "driver_id": "2a1a58df-787e-4522-a0b9-5966f2d2fb1f"
     */
