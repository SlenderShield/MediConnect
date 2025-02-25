package com.hungrycoders.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/doctor")
    public String doctorFallback() {
        return "Doctor Service is temporarily unavailable. Please try again later.";
    }

    @GetMapping("/patient")
    public String patientFallback() {
        return "Patient Service is temporarily unavailable. Please try again later.";
    }

    @GetMapping("/appointment")
    public String appointmentFallback() {
        return "Appointment Service is temporarily unavailable. Please try again later.";
    }

    @GetMapping("/auth")
    public String authFallback() {
        return "Authentication Service is temporarily unavailable. Please try again later.";
    }
}
