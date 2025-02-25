package com.hungrycoders.controller;

import com.hungrycoders.exception.ResourceNotFoundException;
import com.hungrycoders.payload.request.Patient;
import com.hungrycoders.payload.response.GenericResponse;
import com.hungrycoders.services.PatientService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("/api/v1/patient")
public class PatientController {

    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    private PatientService patientService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getPatientById(@PathVariable String id) throws ResourceNotFoundException {
        try {
            com.hungrycoders.model.Patient patient = patientService.getPatientById(id);
            return ResponseEntity.status(200).body(new GenericResponse<>("Patient fetched successfully", patientService.getPatientById(id)));
        } catch (Exception e) {
            logger.error("Error fetching patient with id: {} {}", id, e.getMessage());
            String errorMessage = "Error fetching patient's details";
            if (e.getMessage() != null && !e.getMessage().isBlank()) {
                errorMessage += ": " + e.getMessage();
            }
            return ResponseEntity.status(500).body(new GenericResponse<>(errorMessage));
        }

    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getPatientByEmail(@PathVariable String email) throws ResourceNotFoundException {
        try {
            com.hungrycoders.model.Patient patient = patientService.getPatientByEmail(email);
            return ResponseEntity.status(200).body(new GenericResponse<>("Patient fetched successfully", patient));
        } catch (Exception e) {
            logger.error("Error fetching patient with email: {} {}", email, e.getMessage());
            String errorMessage = "Error fetching patient's details";
            if (e.getMessage() != null && !e.getMessage().isBlank()) {
                errorMessage += ": " + e.getMessage();
            }
            return ResponseEntity.status(500).body(new GenericResponse<>(errorMessage));
        }

    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllPatients() throws Exception {
        try {
            List<com.hungrycoders.model.Patient> patients = patientService.getAllPatients();
            return ResponseEntity.status(200).body(new GenericResponse<>("Fetched patients successfully", patients));
        } catch (Exception e) {
            logger.error("Error fetching patients: " + e.getMessage());
            String errorMessage = "Error fetching patients";
            if (e.getMessage() != null && !e.getMessage().isBlank()) {
                errorMessage += ": " + e.getMessage();
            }
            return ResponseEntity.status(500).body(new GenericResponse<>(errorMessage));
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> savePatient(@Valid @RequestBody com.hungrycoders.payload.request.Patient patient) throws Exception {
        try {
            com.hungrycoders.model.Patient savedPatient = patientService.addPatient(patient);
            return ResponseEntity.status(200).body(new GenericResponse<>("Patient saved successfully", savedPatient));
        } catch(Exception e) {
            logger.error("Error saving patient: {}", e.getMessage());
            return ResponseEntity.status(500).body(new GenericResponse<>("Error saving patient"));
        }

    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updatePatient(@Valid @RequestBody Patient patient, @PathVariable String id) throws Exception {
        try {
            com.hungrycoders.model.Patient updatedPatient = patientService.updatePatientById(id, patient);
            return ResponseEntity.status(200).body(new GenericResponse<>("Patient updated successfully", updatedPatient));
        } catch(Exception e) {
            logger.error("Error updating patient: " + e.getMessage());
            return ResponseEntity.status(500).body(new GenericResponse<>("Error updating patient"));
        }

    }

}
