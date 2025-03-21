# Devices API
This project is a RESTful API developed to persist and manage device resources, built with Spring Boot 3.4.3, Java 21, and Maven. The application is containerized using Docker, persists data in a PostgreSQL database, and includes comprehensive test coverage and API documentation via Swagger (Springdoc OpenAPI).

## Project Overview

### Device Domain
The API manages devices with the following properties:
- **Id**: Unique identifier (UUID, auto-generated).
- **Name**: Device name (e.g., "Device1").
- **Brand**: Device brand (e.g., "Brand1").
- **State**: Device state, represented as an enum (`AVAILABLE`, `IN_USE`, `INACTIVE`).
- **Creation Time**: Timestamp of device creation (immutable).

### Supported Functionalities
- **Create a new device**: Add a device to the system.
- **Fully and/or partially update an existing device**: Modify device details.
- **Fetch a single device**: Retrieve a device by its ID.
- **Fetch all devices**: List all devices.
- **Fetch devices by brand**: Filter devices by brand.
- **Fetch devices by state**: Filter devices by state.
- **Delete a single device**: Remove a device from the system.

### Domain Validations
- **Creation Time**: Cannot be updated after initial creation.
- **Name and Brand**: Cannot be updated if the device is in the `IN_USE` state.
- **Deletion**: Devices in the `IN_USE` state cannot be deleted.

---

## Acceptance Criteria
- The application compiles and runs successfully.
- Reasonable test coverage is provided (unit tests with JUnit 5 and Mockito).
- The API is documented using Swagger (accessible via `/swagger-ui.html`).
- Resources are persisted in a PostgreSQL database (not in-memory).
- The application is containerized with Docker.
- Delivered as a Git repository with this README containing all necessary documentation.

---

# Setup and Installation

## Requirements
- **Java**: 21+
- **Maven**: 3.9+
- **Docker**: For containerization.
- **PostgreSQL**: Used as the persistent database (configured via Docker).
- **Git**: For version control.

## Steps
1. **Clone the Repository**
   ```bash
   git clone https://github.com/RicardoSoftwareEngineer/Device.git
   cd device
   
2. **Build the Project**
   ```bash
   mvn clean install

3. **Use the provided docker-compose.yml to start the app and database:**
   ```bash
   docker-compose up

#### This will:
- Build the application image (device-api).
- Start a PostgreSQL container (postgres-container) on port 5432.
- Start the Spring Boot app on port 8080.

4. **Access the API**
- **Base URL:**: http://localhost:8080/device/v1
- **Swagger UI:**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON:**: http://localhost:8080/v3/api-docs

5. **Here are the postman requests:**
   https://www.postman.com/digitusforum/workspace/postman/collection/13921718-c58c1432-61b7-4c8b-9508-82abe2aaff86?action=share&creator=13921718


---



