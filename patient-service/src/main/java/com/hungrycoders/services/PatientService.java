package com.hungrycoders.services;

import com.hungrycoders.exception.ResourceNotFoundException;
import com.hungrycoders.model.Patient;
import com.hungrycoders.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PatientService {

    private static final Logger logger = LoggerFactory.getLogger(PatientService.class);

    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> getAllPatients() {
        List<Patient> patientList = patientRepository.findAll();
        logger.debug("Number of patients fetched: {}", patientList.size());
        return new ArrayList<>(patientList);
    }

    public Patient addPatient(com.hungrycoders.payload.request.Patient patient) throws Exception {
        Optional<Patient> optionalPatient = patientRepository.findByEmail(patient.getEmail());
        if(optionalPatient.isPresent()) {
            logger.error("Failed to add patient: A patient with email {} already exists", patient.getEmail());
            throw new Exception("A patient with this email already exists");
        }
        UUID generatedId = UUID.randomUUID();
        Patient patientEntity = new Patient(generatedId,
                patient.getFirstName(), patient.getLastName(),
                patient.getEmail(), patient.getPhone(), patient.getAge());
        logger.debug("Saved patient with id: {}", generatedId);
        return patientRepository.save(patientEntity);
    }

    public Patient getPatientById(String id) throws ResourceNotFoundException {
        Optional<Patient> patient = patientRepository.findById(UUID.fromString(id));
        if(patient.isEmpty()) {
            logger.error("Doctor not found with id: {}", id);
            throw new ResourceNotFoundException("patient not found");
        }
        logger.info("Doctor fetched successfully with id: {}", id);
        return patient.get();
    }

    public Patient getPatientByEmail(String email) throws ResourceNotFoundException {
        Optional<Patient> patient = patientRepository.findByEmail(email);
        if(patient.isEmpty()) {
            logger.error("Doctor not found with email: {}", email);
            throw new ResourceNotFoundException("patient not found");
        }
        logger.info("Doctor fetched successfully with email: {}", email);
        return patient.get();
    }

    public Patient updatePatientById(String id, com.hungrycoders.payload.request.Patient patient) throws ResourceNotFoundException {
        UUID uuid = UUID.fromString(id); // Convert String to UUID

        // Check if the patient exists
        Optional<Patient> existingDoctor = patientRepository.findById(uuid);
        if (existingDoctor.isEmpty()) {
            logger.error("Failed to update patient: Doctor not found with id: {}", id);
            throw new ResourceNotFoundException("Doctor not found with id: " + id);
        }

        // Get the existing patient entity
        Patient patientToUpdate = existingDoctor.get();

        // Update the fields of the existing patient with the new values
        patientToUpdate.setId(uuid);
        patientToUpdate.setFirstName(patient.getFirstName());
        patientToUpdate.setLastName(patient.getLastName());
        patientToUpdate.setEmail(patient.getEmail());
        patientToUpdate.setPhone(patient.getPhone());
        patientToUpdate.setAge(patient.getAge());

        // Log the update operation
        logger.debug("Updated patient with id: {}", id);

        // Save the updated patient entity back to the repository
        return patientRepository.save(patientToUpdate);
    }


    public void deleteDoctorById(String id) throws ResourceNotFoundException {
        UUID uuid = UUID.fromString(id); // Convert String to UUID
        if (!patientRepository.existsById(uuid)) {
            logger.error("Failed to delete patient: Doctor not found with id: {}", id);
            throw new ResourceNotFoundException("patient not found with id: " + id);
        }
        patientRepository.deleteById(uuid); // Delete the patient by UUID
        logger.info("Doctor deleted successfully with id: {}", id);
    }

}


