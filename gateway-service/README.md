
# API Gateway Service

The API Gateway Service serves as the central entry point for all client requests. It handles routing, authentication, and fallback mechanisms for microservices. The service also integrates circuit breakers to improve resiliency and fault tolerance.

---

## How to Run

### Prerequisites
1. Install **Java 17** and **Maven**.
2. Install **Docker Desktop** and **Docker Compose**.

### Steps to Run

Run with Docker Compose:

Build and start all services using Docker Compose. Follow the instructions in the main `README.md` file located in the root folder.

---

## Features

1. **Routing**: Handles routing of requests to microservices (e.g., Auth, Doctor, Patient, Appointment).
2. **Authentication**: Verifies JWT tokens for secured endpoints.
3. **Circuit Breaker**: Implements fallback logic using Resilience4j.
4. **Fallback**: Provides fallback routes for service failures to ensure graceful degradation.

---

## Gateway Configuration

### Routing

The API Gateway routes incoming requests to the respective microservices:

| Service             | Path                        | Fallback Path            | Target URI                     |
|---------------------|-----------------------------|--------------------------|--------------------------------|
| **Doctor Service**  | `/api/v1/doctor/**`         | `/fallback/doctor`       | `http://doctor-service:8080`  |
| **Patient Service** | `/api/v1/patient/**`        | `/fallback/patient`      | `http://patient-service:8080` |
| **Appointment Service** | `/api/v1/appointments/**` | `/fallback/appointment`  | `http://appointment-service:8080` |
| **Auth Service**    | `/api/auth/**`              | `/fallback/auth`         | `http://auth-service:8080`    |

### Open API Endpoints

The following endpoints are publicly accessible without authentication:
- `/api/auth/register`
- `/api/auth/signin`

---

## Authentication and JWT Validation

The API Gateway verifies JWT tokens for secured routes:
- Uses the `RouterValidator` class to validate if a request is secured.
- JWT tokens are validated using the `JwtUtil` component, which checks for:
    - Expiration
    - Signature integrity

---

## Circuit Breaker Configuration

Resilience4j is used to handle failures:
- Each route has a specific circuit breaker (e.g., `doctorCircuitBreaker`).
- Fallback URIs are defined for degraded service responses.

---

## APIs

### Fallback APIs

In case of service failure, fallback responses are provided:

#### 1. **Doctor Service Fallback**
- **Endpoint**: `GET /fallback/doctor`
- **Response**: `Doctor Service is currently unavailable. Please try again later.`

#### 2. **Patient Service Fallback**
- **Endpoint**: `GET /fallback/patient`
- **Response**: `Patient Service is currently unavailable. Please try again later.`

#### 3. **Appointment Service Fallback**
- **Endpoint**: `GET /fallback/appointment`
- **Response**: `Appointment Service is currently unavailable. Please try again later.`

#### 4. **Auth Service Fallback**
- **Endpoint**: `GET /fallback/auth`
- **Response**: `Auth Service is currently unavailable. Please try again later.`

---

## Additional Notes

1. **Environment Variables**:
    - `jwt.secret`: Must be set for JWT token validation.

2. **Dependencies**:
    - `spring-cloud-starter-gateway`: Provides routing and gateway features.
    - `spring-cloud-starter-circuitbreaker-reactor-resilience4j`: Handles circuit breaker and fallback logic.
    - `io.jsonwebtoken`: Used for JWT operations.

3. **Docker Integration**:
    - Ensure all microservices (Auth, Doctor, Patient, Appointment) are running for full functionality.
    - Replace `jwt.secret` in `docker-compose.yml` with a secure value for production.
