package com.hungrycoder.auth;

import com.hungrycoder.auth.models.Role;
import com.hungrycoder.auth.models.UserRole;
import com.hungrycoder.auth.payload.request.SignupRequest;
import com.hungrycoder.auth.repository.RoleRepository;
import com.hungrycoder.auth.services.AuthService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DataInitializer {

    @Autowired
    private AuthService authService;

    @Bean
    public CommandLineRunner initializeData(RoleRepository roleRepository,
                                            MongoTemplate mongoTemplate) {
        return args -> {

            // Check and insert roles if necessary
            if (!mongoTemplate.collectionExists("roles")
                    || mongoTemplate.findAll
                    (Role.class, "roles").isEmpty()) {

                Role adminRole = new Role(UserRole.ROLE_ADMIN);
                roleRepository.save(adminRole);
                System.out.println("Created ROLE_ADMIN");

                Role doctorRole = new Role(UserRole.ROLE_DOCTOR);
                roleRepository.save(doctorRole);
                System.out.println("Created ROLE_DOCTOR");

                Role patientRole = new Role(UserRole.ROLE_PATIENT);
                roleRepository.save(patientRole);
                System.out.println("Created ROLE_PATIENT");
            } else {
                System.out.println("Roles already exist. Skipping role creation.");
            }

            // Check and insert users if necessary
            if (!mongoTemplate.collectionExists("users")) {
                SignupRequest adminUser = new SignupRequest(
                        "admin",
                        "noreplyhungrycoders@gmail.com",
                        new HashSet<>(List.of("admin")),
                        "admin123"
                );
                SignupRequest doctorUser = new SignupRequest(
                        "doctor",
                        "doctorhungrycoders@gmail.com",
                        new HashSet<>(List.of("doctor")),
                        "doctor123"
                );
                SignupRequest patientUser = new SignupRequest(
                        "patient",
                        "patienthungrycoders@gmail.com",
                        new HashSet<>(List.of("patient")),
                        "patient123"
                );

                authService.registerUser(adminUser);
                authService.registerUser(doctorUser);
                authService.registerUser(patientUser);
            } else {
                System.out.println("Users already exist. Skipping user creation.");
            }

            // Check and insert into Doctors collection
            if (!mongoTemplate.collectionExists("doctors")) {
                Document doctor = new Document()
                        .append("firstName", "Sample")
                        .append("lastName", "Doctor")
                        .append("email", "doctorhungrycoders@gmail.com")
                        .append("phone", "123456789")
                        .append("speciality", "Gynic, General Medicine")
                        .append("yearsOfExperience", 4)
                        .append("status", "AVAILABLE")
                        .append("_id", UUID.randomUUID().toString());
                mongoTemplate.insert(doctor, "doctors");
                System.out.println("Inserted sample doctor data.");
            } else {
                System.out.println("Doctors collection already exists. Skipping insertion.");
            }

            // Check and insert into Patients collection
            if (!mongoTemplate.collectionExists("patients")) {
                Document patient = new Document()
                        .append("firstName", "Sample")
                        .append("lastName", "Patient")
                        .append("email", "patienthungrycoders@gmail.com")
                        .append("phone", "123456789")
                        .append("age", 30)
                        .append("_id", UUID.randomUUID().toString());
                mongoTemplate.insert(patient, "patients");
                System.out.println("Inserted sample patient data.");
            } else {
                System.out.println("Patients collection already exists. Skipping insertion.");
            }

            System.out.println("Data initialization complete!");
        };
    }
}
