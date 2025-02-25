package com.hungrycoders.repository;

import com.hungrycoders.model.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DoctorRepository extends MongoRepository<Doctor, UUID> {
    // Custom query methods (optional)
    Optional<Doctor> findByEmail(String email);
}