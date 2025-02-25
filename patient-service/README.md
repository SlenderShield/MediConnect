
# Patient Service

The Patient Service handles all operations related to patients in the healthcare system. It provides APIs for managing patient details, including fetching, updating, and saving patient information.

---

## How to Run

### Prerequisites
1. Install Java 17 and Maven.
2. Install Docker Desktop and Docker Compose.
3. Install MongoDB Compass to view data.

### Steps to Run

Run with Docker Compose:

Build and start all services using Docker Compose.
Follow the instructions in the main README.md file in the root folder.

---

## APIs

### 1. **Get Patient by ID**
- **Endpoint**: `GET /api/v1/patient/{id}`
- **Description**: Fetches details of a patient by their unique ID.
- **Response**:
    ```json
    {
        "message": "Patient fetched successfully",
        "data": {
            "id": "patientId",
            "firstName": "John",
            "lastName": "Doe",
            "email": "john.doe@example.com",
            "phone": "1234567890",
            "age": 30
        }
    }
    ```

---

### 2. **Get Patient by Email**
- **Endpoint**: `GET /api/v1/patient/email/{email}`
- **Description**: Fetches details of a patient by their email address.
- **Response**:
    ```json
    {
        "message": "Patient fetched successfully",
        "data": {
            "id": "patientId",
            "firstName": "John",
            "lastName": "Doe",
            "email": "john.doe@example.com",
            "phone": "1234567890",
            "age": 30
        }
    }
    ```

---

### 3. **Get All Patients**
- **Endpoint**: `GET /api/v1/patient/all`
- **Description**: Fetches details of all patients.
- **Response**:
    ```json
    {
        "message": "Fetched patients successfully",
        "data": [
            {
                "id": "patientId1",
                "firstName": "John",
                "lastName": "Doe",
                "email": "john.doe@example.com",
                "phone": "1234567890",
                "age": 30
            },
            {
                "id": "patientId2",
                "firstName": "Jane",
                "lastName": "Doe",
                "email": "jane.doe@example.com",
                "phone": "0987654321",
                "age": 25
            }
        ]
    }
    ```

---

### 4. **Save Patient**
- **Endpoint**: `POST /api/v1/patient/`
- **Description**: Saves a new patient's details.
- **Request Body**:
    ```json
    {
        "firstName": "John",
        "lastName": "Doe",
        "email": "john.doe@example.com",
        "phone": "1234567890",
        "age": 30
    }
    ```
- **Response**:
    ```json
    {
        "message": "Patient saved successfully",
        "data": {
            "id": "patientId",
            "firstName": "John",
            "lastName": "Doe",
            "email": "john.doe@example.com",
            "phone": "1234567890",
            "age": 30
        }
    }
    ```

---

### 5. **Update Patient**
- **Endpoint**: `PATCH /api/v1/patient/{id}`
- **Description**: Updates details of an existing patient.
- **Request Body**:
    ```json
    {
        "firstName": "John",
        "lastName": "Smith",
        "email": "john.smith@example.com",
        "phone": "1234567890",
        "age": 35
    }
    ```
- **Response**:
    ```json
    {
        "message": "Patient updated successfully",
        "data": {
            "id": "patientId",
            "firstName": "John",
            "lastName": "Smith",
            "email": "john.smith@example.com",
            "phone": "1234567890",
            "age": 35
        }
    }
    ```

---

## Additional Notes

- Ensure all required environment variables like `MONGO_URI` are set correctly in the `docker-compose.yml` file.
- All services, including the Patient Service, are registered in Spring Boot Admin for centralized monitoring.
