package com.hungrycoders.repository;

import com.hungrycoders.model.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository extends MongoRepository<Patient, UUID> {
    // Custom query methods (optional)
    Optional<Patient> findByEmail(String email);
}