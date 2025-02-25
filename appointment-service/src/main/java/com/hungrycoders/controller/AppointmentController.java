package com.hungrycoders.controller;

import com.hungrycoders.model.Appointment;
import com.hungrycoders.payload.request.AppointmentRequest;
import com.hungrycoders.service.AppointmentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing appointments, providing endpoints for booking,
 * updating, and retrieving appointments based on criteria.
 */
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentController.class);

    @Autowired
    private AppointmentService appointmentService;

    /**
     * Books a new appointment.
     *
     * @param appointmentRequest the appointment details.
     * @return a response entity containing the result or an error message.
     */
    @PostMapping("/create")
    public ResponseEntity<String> bookAppointment(@Valid @RequestBody AppointmentRequest appointmentRequest) {
        logger.info("Booking appointment: {}", appointmentRequest);
        try {
            String result = appointmentService.bookAppointment(appointmentRequest);
            logger.info("Appointment booked successfully: {}", result);
            return ResponseEntity.ok("Appointment booked successfully. ID: " + result);
        } catch (Exception e) {
            logger.error("Error booking appointment: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Unable to book appointment.");
        }
    }

    /**
     * Retrieves appointments for a specific doctor by ID.
     *
     * @param doctorId the unique ID of the doctor.
     * @return a response entity with the list of appointments or an error message.
     */
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<?> getAppointmentsByDoctorId(@PathVariable String doctorId) {
        logger.info("Fetching appointments for doctor ID: {}", doctorId);
        try {
            List<Appointment> appointments = appointmentService.getByDoctorId(doctorId);
            logger.info("Appointments fetched successfully for doctor ID: {}", doctorId);
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            logger.error("Error fetching appointments for doctor ID {}: {}", doctorId, e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Unable to fetch appointments.");
        }
    }

    /**
     * Retrieves appointments for a specific patient by ID.
     *
     * @param patientId the unique ID of the patient.
     * @return a response entity with the list of appointments or an error message.
     */
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<?> getAppointmentsByPatientId(@PathVariable String patientId) {
        logger.info("Fetching appointments for patient ID: {}", patientId);
        try {
            List<Appointment> appointments = appointmentService.getByPatientId(patientId);
            logger.info("Appointments fetched successfully for patient ID: {}", patientId);
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            logger.error("Error fetching appointments for patient ID {}: {}", patientId, e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Unable to fetch appointments.");
        }
    }

    /**
     * Retrieves all appointments.
     *
     * @return a response entity containing all appointments or an error message.
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAllAppointments() {
        logger.info("Fetching all appointments.");
        try {
            List<Appointment> appointments = appointmentService.getAllAppointments();
            logger.info("All appointments fetched successfully.");
            return ResponseEntity.ok(appointments);
        } catch (Exception e) {
            logger.error("Error fetching all appointments: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Unable to fetch appointments.");
        }
    }

    /**
     * Updates an existing appointment.
     *
     * @param appointmentRequest the updated appointment details.
     * @return a response entity with the result or an error message.
     */
    @PutMapping
    public ResponseEntity<String> updateAppointment(@Valid @RequestBody AppointmentRequest appointmentRequest) {
        logger.info("Updating appointment: {}", appointmentRequest);
        try {
            String result = appointmentService.updateAppointment(appointmentRequest);
            logger.info("Appointment updated successfully: {}", result);
            return ResponseEntity.ok("Appointment updated successfully. ID: " + result);
        } catch (Exception e) {
            logger.error("Error updating appointment: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Unable to update appointment.");
        }
    }
}
