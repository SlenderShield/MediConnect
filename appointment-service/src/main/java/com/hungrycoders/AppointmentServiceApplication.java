package com.hungrycoders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Appointment Service application.
 *
 * This Spring Boot application manages appointments between doctors and patients.
 */
@SpringBootApplication
public class AppointmentServiceApplication {

    /**
     * Main method to start the Spring Boot application.
     *
     * @param args command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(AppointmentServiceApplication.class, args);
    }
}
