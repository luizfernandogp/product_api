# Use a base image with JDK 21
FROM eclipse-temurin:21-jdk-alpine as build

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle build files
COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradle ./gradle

# Copy the project source
COPY src ./src

# Build the application
RUN ./gradlew build -x test

# Stage 2: Build the runtime image
FROM eclipse-temurin:21-jre-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Set the entry point for the container to run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar", "--spring.profiles.active=qa"]
