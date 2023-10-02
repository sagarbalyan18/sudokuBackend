# Stage 1: Build the Spring Boot application using Maven
FROM maven:3.8.2-openjdk-17-slim as builder

# Set the working directory in the container
WORKDIR /app

# Copy the Maven project file and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the application source code into the container
COPY src/ ./src/

# Build the Spring Boot application
RUN mvn package

# Stage 2: Create the final Docker image with Java 17
FROM openjdk:11

# Set the working directory in the container
WORKDIR /app

# Copy the compiled JAR file from the builder stage
COPY --from=builder /app/target/your-springboot-app.jar app.jar

# Expose the port that the Spring Boot application will run on
EXPOSE 8080

# Start the Spring Boot application
CMD ["java", "-jar", "app.jar"]
