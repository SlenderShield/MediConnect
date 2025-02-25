package com.hungrycoders.controller;

import com.hungrycoders.exception.ResourceNotFoundException;
import com.hungrycoders.model.Doctor;
import com.hungrycoders.payload.response.GenericResponse;
import com.hungrycoders.services.DoctorService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping("/api/v1/doctor")
public class DoctorController {

    private static final Logger logger = LoggerFactory.getLogger(DoctorController.class);

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable String id) throws ResourceNotFoundException {
        try {
            Doctor doctor = doctorService.getDoctorById(id);
            return ResponseEntity.status(200).body(new GenericResponse<>("Doctor fetched successfully", doctor));
        } catch (Exception e) {
            logger.error("Error fetching doctor with id: {} {}", id, e.getMessage());
            String errorMessage = "Error fetching doctor's details";
            if (e.getMessage() != null && !e.getMessage().isBlank()) {
                errorMessage += ": " + e.getMessage();
            }
            return ResponseEntity.status(500).body(new GenericResponse<>(errorMessage));
        }

    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getDoctorByEmail(@PathVariable String email) throws ResourceNotFoundException {
        try {
            Doctor doctor = doctorService.getDoctorByEmail(email);
            return ResponseEntity.status(200).body(new GenericResponse<>("Doctor fetched successfully", doctor));
        } catch (Exception e) {
            logger.error("Error fetching doctor with email: {} {}", email, e.getMessage());
            String errorMessage = "Error fetching doctor's details";
            if (e.getMessage() != null && !e.getMessage().isBlank()) {
                errorMessage += ": " + e.getMessage();
            }
            return ResponseEntity.status(500).body(new GenericResponse<>(errorMessage));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllDoctors() throws Exception {
        try {
            List<Doctor> doctors = doctorService.getAllDoctors();
            return ResponseEntity.status(200).body(new GenericResponse<>("Fetched doctors successfully", doctors));
        } catch (Exception e) {
            logger.error("Error fetching doctors: " + e.getMessage());
            String errorMessage = "Error fetching doctors";
            if (e.getMessage() != null && !e.getMessage().isBlank()) {
                errorMessage += ": " + e.getMessage();
            }
            return ResponseEntity.status(500).body(new GenericResponse<>(errorMessage));
        }
    }

    @PostMapping
    public ResponseEntity<?> saveDoctor(@Valid @RequestBody com.hungrycoders.payload.request.Doctor doctor) throws Exception {
        try {
            Doctor savedDoctor = doctorService.addDoctor(doctor);
            return ResponseEntity.status(200).body(new GenericResponse<>("Doctor saved successfully", savedDoctor));
        } catch(Exception e) {
            logger.error("Error saving doctor: " + e.getMessage());
            return ResponseEntity.status(500).body(new GenericResponse<>("Error saving doctor"));
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDoctor(@Valid @RequestBody com.hungrycoders.payload.request.Doctor doctor, @PathVariable String id) throws Exception {
        try {
            Doctor updatedDoctor = doctorService.updateDoctorById(id, doctor);
            return ResponseEntity.status(200).body(new GenericResponse<>("Doctor updated successfully", updatedDoctor));
        } catch(Exception e) {
            logger.error("Error updating doctor: " + e.getMessage());
            return ResponseEntity.status(500).body(new GenericResponse<>("Error updating doctor"));
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable String id) throws Exception {
        try {
            doctorService.deleteDoctorById(id);
            return ResponseEntity.status(200).body(new GenericResponse<>("Doctor deleted successfully"));
        } catch(Exception e) {
            logger.error("Error deleting doctor: " + e.getMessage());
            return ResponseEntity.status(500).body(new GenericResponse<>("Error deleting doctor"));
        }

    }
}
