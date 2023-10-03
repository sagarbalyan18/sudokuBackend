
#Stage 1
# initialize build and set base image for first stage
FROM maven:3.8.4-openjdk-17-slim as stage1
# speed up Maven JVM a bit
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"
# set working directory
WORKDIR /opt/demo
# copy just pom.xml
COPY pom.xml .
# go-offline using the pom.xml
RUN mvn dependency:go-offline
# copy your other files
COPY ./src ./src
# compile the source code and package it in a jar file
RUN mvn clean install -Dmaven.test.skip=true
#Stage 2
# set base image for second stage
FROM openjdk:11
# set deployment directory
WORKDIR /opt/demo
# copy over the built artifact from the maven image
COPY --from=stage1 /opt/demo/target/demo.jar app.jar

EXPOSE 8080

# Start the Spring Boot application
CMD ["java", "-jar", "app.jar"]
