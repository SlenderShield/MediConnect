package com.hungrycoders.repository;

import com.hungrycoders.model.Appointment;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for managing appointments in the database.
 */
public interface AppointmentRepo extends MongoRepository<Appointment, UUID> {

    /**
     * Finds appointments by doctor ID, sorted as specified.
     *
     * @param doctorId the ID of the doctor.
     * @param sort the sorting criteria.
     * @return a list of appointments for the doctor.
     */
    @Query("{'doctor.id': ?0}")
    List<Appointment> findByDoctorId(String doctorId, Sort sort);

    /**
     * Finds appointments by patient ID, sorted as specified.
     *
     * @param patientId the ID of the patient.
     * @param sort the sorting criteria.
     * @return a list of appointments for the patient.
     */
    @Query("{'patient.id': ?0}")
    List<Appointment> findByPatientId(String patientId, Sort sort);

    /**
     * Retrieves all appointments sorted by appointment time in ascending order.
     *
     * @return a list of all appointments.
     */
    List<Appointment> findAllByOrderByAppointmentTimeAsc();
}