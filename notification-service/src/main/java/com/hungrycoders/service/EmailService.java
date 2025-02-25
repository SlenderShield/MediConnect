package com.hungrycoders.service;

import com.hungrycoders.model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    private static final String APPOINTMENT_PENDING_SUBJECT = "Appointment Pending Approval";
    private static final String APPOINTMENT_CONFIRMED_SUBJECT = "Appointment Confirmed";
    private static final String APPOINTMENT_REJECTED_SUBJECT = "Appointment Rejected";
    private static final String APPOINTMENT_UPDATED_SUBJECT = "Appointment Update";
    private static final String APPOINTMENT_COMPLETED_SUBJECT = "Appointment Completed";

    private static final String APPOINTMENT_PENDING_EMAIL_BODY = "Dear Doctor,\n\nYour appointment with Patient: %s is currently pending approval.\nAppointment Date and Time: %s\nNotes: %s\nDoctor's Comments: %s\n\nThank you,\nTeam HungryCoders";
    private static final String APPOINTMENT_CONFIRMED_EMAIL_BODY = "Dear Patient,\n\nYour appointment with Doctor ID: %s has been confirmed.\nAppointment Date and Time: %s\nNotes: %s\nDoctor's Comments: %s\n\nThank you,\nTeam HungryCoders";
    private static final String APPOINTMENT_REJECTED_EMAIL_BODY = "Dear Patient,\n\nWe regret to inform you that your appointment with Doctor ID: %s has been rejected.\nPlease contact support for further assistance.\nNotes: %s\nDoctor's Comments: %s\n\nThank you,\nTeam";
    private static final String APPOINTMENT_UPDATED_EMAIL_BODY = "Dear Patient,\n\nYour appointment with Doctor ID: %s has been updated.\nAppointment Date and Time: %s\nNotes: %s\nDoctor's Comments: %s\n\nThank you,\nTeam HungryCoders";
    private static final String APPOINTMENT_COMPLETED_EMAIL_BODY = "Dear Patient,\n\nYour appointment with Doctor ID: %s has been successfully completed.\nAppointment Date and Time: %s\nNotes: %s\nDoctor's Comments: %s\n\nThank you,\nTeam HungryCoders";

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, hh:mm a");

    @Value("${spring.mail.username}")
    private String FROM_ADDRESS;

    @Autowired
    private JavaMailSender emailSender;

    private void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(FROM_ADDRESS);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            emailSender.send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
        }
    }

    public void triggerEmailNotification(Appointment appointment) {
        String to, subject, text;
        String doctor = appointment.getDoctor().getFirstName() + " " + appointment.getDoctor().getLastName();
        String patient = appointment.getPatient().getFirstName() + " " + appointment.getPatient().getLastName();
        String appointmentNotes = appointment.getNotes().isEmpty() ? "N/A" : appointment.getNotes();
        String doctorComments = appointment.getDoctorComments().isEmpty() ? "N/A" : appointment.getDoctorComments();

        switch (appointment.getStatus()) {
            case PENDING:
                to = appointment.getDoctor().getEmail();
                subject = APPOINTMENT_PENDING_SUBJECT;
                text = String.format(
                        APPOINTMENT_PENDING_EMAIL_BODY,
                        patient,
                        appointment.getAppointmentTime().format(dateTimeFormatter),
                        appointmentNotes,
                        doctorComments
                );
                break;

            case CONFIRMED:
                to = appointment.getPatient().getEmail();
                subject = APPOINTMENT_CONFIRMED_SUBJECT;
                text = String.format(
                        APPOINTMENT_CONFIRMED_EMAIL_BODY,
                        doctor,
                        appointment.getAppointmentTime().format(dateTimeFormatter),
                        appointmentNotes,
                        doctorComments
                );
                break;

            case REJECTED:
                to = appointment.getPatient().getEmail();
                subject = APPOINTMENT_REJECTED_SUBJECT;
                text = String.format(
                        APPOINTMENT_REJECTED_EMAIL_BODY,
                        doctor,
                        appointmentNotes,
                        doctorComments
                );
                break;

            case COMPLETED:
                to = appointment.getPatient().getEmail();
                subject = APPOINTMENT_COMPLETED_SUBJECT;
                text = String.format(
                        APPOINTMENT_COMPLETED_EMAIL_BODY,
                        doctor,
                        appointment.getAppointmentTime().format(dateTimeFormatter),
                        appointmentNotes,
                        doctorComments
                );
                break;

            default:
                to = appointment.getPatient().getEmail();
                subject = APPOINTMENT_UPDATED_SUBJECT;
                text = String.format(
                        APPOINTMENT_UPDATED_EMAIL_BODY,
                        doctor,
                        appointment.getAppointmentTime().format(dateTimeFormatter),
                        appointmentNotes,
                        doctorComments
                );
        }

        sendSimpleMessage(to, subject, text);
    }
}
