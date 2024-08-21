## How to Run the Project

To run the project locally, follow these steps:

### Prerequisites

Ensure you have the following installed on your system:
- **Java 21**: The project is configured to use Java 21. Make sure it's installed and set as the default Java version.
- **Gradle**: Although the project uses the Gradle Wrapper, having Gradle installed can be helpful for managing dependencies and builds.
- **Docker**: Required for containerizing the application and its dependencies.
- **Docker Compose**: Needed for defining and running multi-container Docker applications.

### Testing Environment

This project was fully tested using the Docker image `eclipse-temurin:21-jdk-alpine`. This image provides a lightweight, production-ready environment with Java 21, ensuring compatibility and consistent behavior across different systems.

### How to Run

After setting up your environment, you can build and start the application using Docker Compose:

1. **Build env check:**

    ```bash
    ./gradlew build
    ```

2. **Build the Docker images:**

    ```bash
    docker-compose build
    ```

3. **Start the application:**

    ```bash
    docker-compose up
    ```

These commands will build the Docker images as defined in your `docker-compose.yml` file and start the application along with its dependencies.

## General Information
- **Group**: `com.luizgomes`
- **Version**: `1.0.0-SNAPSHOT`
- **Java Version**: `21`

## Main Frameworks and Libraries
- **Spring Boot**: `3.3.2`
    - A powerful framework for building Java-based web applications with embedded servers and production-ready features.
- **Spring Dependency Management**: `1.1.6`
    - Ensures consistent dependency versions throughout the project.

## Key Dependencies
- **Spring Boot Starter Web**
    - Provides the core features for building web applications, including RESTful services.
- **Spring Boot Starter Data JPA**
    - Simplifies database interactions using Java Persistence API (JPA) and Hibernate.
- **Spring Boot Starter Validation**
    - Adds support for bean validation using annotations.
- **Spring Boot Starter Actuator**
    - Offers production-ready features like monitoring and health checks.
- **Springdoc OpenAPI**: `2.6.0`
    - Enables OpenAPI 3.0 documentation for REST APIs.
- **Lombok**: `1.18.34`
    - Automates the generation of boilerplate code (e.g., getters, setters, constructors).
- **MapStruct**: `1.5.5.Final`
    - Facilitates the mapping between different Java bean types.
- **PostgreSQL Driver**
    - Allows the application to connect to a PostgreSQL database.

## Testing Frameworks
- **JUnit 5**: `5.10.0`
    - The modern standard for unit and integration testing in Java.
- **Karate**: `1.4.0`
    - A framework for API testing, supporting Gherkin syntax, mocking, and service virtualization.
- **H2 Database**
    - An in-memory database used primarily for testing purposes.

## Code Coverage
- **JaCoCo**: `0.8.12`
    - Generates code coverage reports, showing which parts of the code are covered by tests.

## Build Tool
- **Gradle**:
    - The project uses Gradle with Kotlin DSL (`build.gradle.kts`) for build automation.

## File Information
- **File Name**: `build.gradle.kts`
- **File Extension**: `.kts`
- **File Path**: `/path/to/your/project/build.gradle.kts`
