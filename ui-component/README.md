# healthcare-client

A React-based client-side application for a smart healthcare system. This project provides a user-friendly interface to manage patients, appointments, and more
## Features

- User authentication and protected routes.
- Components for managing doctors, patients, appointments, and navigation.
- Role based protected routes

## Getting Started

### Prerequisites

Ensure you have the following installed:

- Node.js
- npm or yarn

## Scripts

- `dev`: Start the development server.
- `build`: Build the project for production.

---

## API Integrations

The UI Component interacts with the following APIs:

1. **Auth Service**:
   - **Signin:** `/api/auth/signin`

2. **Doctor Service**:
   - **Add Doctor:** `/api/v1/doctor`
   - **Get All Doctors:** `/api/v1/doctor/all`
   - **Get Doctor by ID:** `/api/v1/doctor/{id}`
   - **Update Doctor:** `/api/v1/doctor/{id}`
   - **Delete Doctor:** `/api/v1/doctor/{id}`

3. **Patient Service**:
   - **Get All Patients:** `/api/v1/patient/all`
   - **Get Patient by Email:** `/api/v1/patient/email/{email}`
   - **Delete Patient:** `/api/v1/patient/{id}`

4. **Appointment Service**:
   - **Add Appointment:** `/api/v1/appointments/create`
   - **Get All Appointments:** `/api/v1/appointments/all`
   - **Get Appointments by Doctor ID:** `/api/v1/appointments/doctor/{doctorId}`
   - **Get Appointments by Patient ID:** `/api/v1/appointments/patient/{patientId}`
   - **Update Appointment:** `/api/v1/appointments`
   - **Delete Appointment:** `/api/v1/appointments/{id}`

---

## Dependencies

This project uses the following main dependencies:

- React
- React Router DOM
- Axios (for HTTP requests)
- Other libraries as listed in `package.json`
