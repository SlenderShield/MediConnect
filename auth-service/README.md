
# Auth Service

Auth Service provides authentication and authorization capabilities for your application. It includes APIs for user registration (`signup`) and login (`signin`), generating JWT tokens for secure communication between clients and services.

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

### 1. **Signup API**
- **Endpoint**: `POST /api/auth/signup`
- **Description**: Registers a new user with a username, email, roles, and password.
- **Request Body**:
    ```json
    {
        "username": "aman",
        "email": "aman@google.com",
        "roles": ["ROLE_ADMIN"],
        "password": "Admin@123"
    }
    ```
- **Response**: Returns success or error message based on the operation's outcome.

---

### 2. **Signin API**
- **Endpoint**: `POST /api/auth/signin`
- **Description**: Authenticates a user and provides a JWT token.
- **Request Body**:
    ```json
    {
        "username": "aman",
        "password": "Admin@123"
    }
    ```
- **Response**:
    ```json
    {
        "token": "eyJhbGciOiJIUzIINiJ9.eyJzdWIiOiJhbWFuIiwicm9sZSI6I|JPTEVIUEFUSUVOVCIsImIhdCI6MTczNTI5MjA5NCwiZXhwIjoxNzM1Mzc4NDk0fQ.aZ_N7UBbJQQX_8z_4VXsmiUR_KZclossHYsCXt2_isk",
        "type": "Bearer",
        "id": "676e742b58574f384989b0af",
        "username": "aman",
        "email": "aman@google.com",
        "roles": [
            "ROLE_PATIENT"
        ]
    }
    ```

## Data Initialization

### DataInitializer Class

The DataInitializer class seeds initial data such as roles and sample users into MongoDB.
You must update the data and email IDs in this class before running the project for testing.

---

## Additional Notes

- Ensure email functionality by configuring `MAIL_SERVER_USERNAME` and `MAIL_SERVER_PASSWORD` in the `docker-compose.yml`.
- All services, including the `auth-service`, are registered in Spring Boot Admin for centralized monitoring.
