# Stage 1: Build the Spring Boot application
FROM maven:3.9.4-eclipse-temurin-21-alpine AS build

# Set the working directory inside the container
WORKDIR /app

# Copy only the necessary files for dependency resolution
COPY pom.xml ./
COPY src ./src

# Run Maven clean install to build the application
RUN mvn clean install -DskipTests

# Stage 2: Build the custom JRE
FROM amazoncorretto:21-alpine3.18 AS corretto-jdk

# Install necessary tools for `jlink`
RUN apk add --no-cache binutils

# Use `jlink` to create a lightweight custom JRE
RUN $JAVA_HOME/bin/jlink \
    --verbose \
    --add-modules ALL-MODULE-PATH \
    --strip-debug \
    --no-man-pages \
    --no-header-files \
    --compress=2 \
    --output /customjre

# Stage 3: Create the application image
FROM alpine:latest

# Define environment variables for the custom JRE
ENV JAVA_HOME=/jre
ENV PATH="${JAVA_HOME}/bin:${PATH}"

# Copy the custom JRE from the second stage
COPY --from=corretto-jdk /customjre $JAVA_HOME

# Add a non-root user to run the application
ARG APPLICATION_USER=appuser
RUN adduser --no-create-home -u 1000 -D $APPLICATION_USER

# Configure the working directory and set permissions
RUN mkdir /app && \
    chown -R $APPLICATION_USER /app
USER 1000

# Copy the Spring Boot JAR from the build stage
COPY --from=build /app/target/doctor-service-0.0.1-SNAPSHOT.jar /app/app.jar
WORKDIR /app

# Expose the application port
EXPOSE 8080

# Define the entry point
ENTRYPOINT [ "/jre/bin/java", "-Dspring.profiles.active=prod", "-jar", "/app/app.jar" ]
