# Use an official Maven image as the build environment
FROM maven:3.8-openjdk-11 as builder

# Set the working directory in the container
WORKDIR /app

# Copy the Maven project file and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the application source code into the container
COPY src/ ./src/

# Build the Spring Boot application
RUN mvn package

# Use a lightweight OpenJDK JRE image for the runtime environment
FROM openjdk:11-jre-slim

# Set the working directory in the container
WORKDIR /app

# Copy the compiled JAR file from the builder stage
COPY --from=builder /app/target/sudokuprime-1.0.0-SNAPSHOT.jar app.jar

# Expose the port that the Spring Boot application will run on
EXPOSE 8080

# Start the Spring Boot application
CMD ["java", "-jar", "app.jar"]
