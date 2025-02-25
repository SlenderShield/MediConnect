
# Notification Service

Notification Service listens to Kafka events related to appointments and sends notifications to doctors and patients based on the status of the appointment. Notifications are delivered via email using the configured SMTP settings.

---

## How to Run

### Prerequisites
1. Install Java 17 and Maven.
2. Install Docker Desktop and Docker Compose.
3. Set up an SMTP email account (e.g., Gmail) for sending notifications.

### Steps to Run
1. **Run with Docker Compose**  
   Follow the instructions in the main `README.md` file in the root folder to build and start all services using Docker Compose.

2. **Configure Email Settings**  
   Update the `docker-compose.yml` file with the following environment variables for email functionality:
    - `MAIL_SERVER_HOST`: e.g., `smtp.gmail.com`
    - `MAIL_SERVER_PORT`: e.g., `587`
    - `MAIL_SERVER_USERNAME`: Your email address
    - `MAIL_SERVER_PASSWORD`: Your email password or app-specific password

---

## Kafka Listener

The service listens to appointment-related events from the Kafka topic specified in the configuration (`spring.kafka.topic.name`). Events include details about appointments such as their status and associated doctor/patient information.

---

## Email Functionality

The `EmailService` sends emails based on the status of the appointment:

1. **Pending**
    - **Recipient**: Doctor
    - **Subject**: Appointment Pending Approval
    - **Email Body**: Includes the patient's name and the date/time of the appointment.
    - **Example**:
      ```
      Dear Doctor,
      
      Your appointment with Patient: John Doe is currently pending approval.
      Appointment Date and Time: 24 January 2025, 03:00 PM
      Notes: eye pain
      Doctor's Comments: N/A
      
      Thank you,
      Team HungryCoders
      ```

2. **Confirmed**
    - **Recipient**: Patient
    - **Subject**: Appointment Confirmed
    - **Email Body**: Includes the doctor's name and the date/time of the appointment.
    - **Example**:
      ```
      Dear Patient,
      
      Your appointment with Doctor: Dr. Smith has been confirmed.
      Appointment Date and Time: 24 January 2025, 03:00 PM
      Notes: eye pain
      Doctor's Comments: N/A
      
      Thank you,
      Team HungryCoders
      ```

3. **Rejected**
    - **Recipient**: Patient
    - **Subject**: Appointment Rejected
    - **Email Body**: Includes the doctor's name and informs the patient that the appointment has been rejected.
    - **Example**:
      ```
      Dear Patient,
      
      We regret to inform you that your appointment with Doctor: Dr. Smith has been rejected.
      Please contact support for further assistance.
      Notes: eye pain
      Doctor's Comments: N/A
      
      Thank you,
      Team HungryCoders
      ```

4. **Completed**
    - **Recipient**: Patient
    - **Subject**: Appointment Completed
    - **Email Body**: Includes the doctor's name and updated details of the appointment.
    - **Example**:
      ```
      Dear Patient,
      
      Your appointment with Doctor: Dr. Smith has been updated.
      Appointment Date and Time: 25 January 2025, 04:00 PM
      Notes: eye pain
      Doctor's Comments: eye drop 3 times a day
      
      Thank you,
      Team HungryCoders
      ```

---

## Additional Notes
- Ensure that the email credentials used in the configuration have permission to send emails via SMTP.
- Emails are triggered automatically when the Kafka listener processes an appointment event.
- This service is a Kafka consumer and requires the Kafka topic to be active for proper functionality.
