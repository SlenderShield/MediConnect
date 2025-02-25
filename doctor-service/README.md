
# Doctor Service

The Doctor Service is responsible for managing doctor-related data and operations. It provides APIs for CRUD operations and allows fetching doctor information based on different criteria.

---

## How to Run

### Prerequisites
1. Install Java 17 and Maven.
2. Install Docker Desktop and Docker Compose.
3. Install MongoDB Compass to view data.

### Steps to Run

Run with Docker Compose:

Build and start all services using Docker Compose. Follow the instructions in the main README.md file in the root folder.

---

## APIs

### 1. **Get Doctor by ID**
- **Endpoint**: `GET /api/v1/doctor/{id}`
- **Description**: Fetches doctor details by ID.
- **Response**:
    ```json
    {
        "message": "Doctor fetched successfully",
        "data": {
            "id": "123",
            "firstName": "John",
            "lastName": "Doe",
            "email": "john.doe@example.com",
            "phone": "1234567890",
            "speciality": "Cardiology",
            "yearsOfExperience": 10,
            "status": "ACTIVE"
        }
    }
    ```

---

### 2. **Get Doctor by Email**
- **Endpoint**: `GET /api/v1/doctor/email/{email}`
- **Description**: Fetches doctor details by email.
- **Response**:
    ```json
    {
        "message": "Doctor fetched successfully",
        "data": { ... }
    }
    ```

---

### 3. **Get All Doctors**
- **Endpoint**: `GET /api/v1/doctor/all`
- **Description**: Fetches a list of all doctors.
- **Response**:
    ```json
    {
        "message": "Fetched doctors successfully",
        "data": [
            { ... }, { ... }
        ]
    }
    ```

---

### 4. **Add Doctor**
- **Endpoint**: `POST /api/v1/doctor`
- **Description**: Adds a new doctor to the database.
- **Request Body**:
    ```json
    {
        "firstName": "Jane",
        "lastName": "Smith",
        "email": "jane.smith@example.com",
        "phone": "0987654321",
        "speciality": "Pediatrics",
        "yearsOfExperience": 5,
        "status": "ACTIVE"
    }
    ```
- **Response**:
    ```json
    {
        "message": "Doctor saved successfully",
        "data": { ... }
    }
    ```

---

### 5. **Update Doctor**
- **Endpoint**: `PUT /api/v1/doctor/{id}`
- **Description**: Updates an existing doctor.
- **Request Body**: Same as Add Doctor.
- **Response**:
    ```json
    {
        "message": "Doctor updated successfully",
        "data": { ... }
    }
    ```

---

### 6. **Delete Doctor**
- **Endpoint**: `DELETE /api/v1/doctor/{id}`
- **Description**: Deletes a doctor by ID.
- **Response**:
    ```json
    {
        "message": "Doctor deleted successfully"
    }
    ```
---

## Additional Notes

- Ensure MongoDB is running and accessible as per the configured `MONGO_URI`.
- All services, including the `doctor-service`, are registered in Spring Boot Admin for centralized monitoring.
