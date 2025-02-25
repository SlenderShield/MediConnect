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

# Install the necessary tools for using `jlink`
RUN apk add --no-cache binutils

# Use `jlink` to create a lightweight custom JRE with only the required modules
RUN $JAVA_HOME/bin/jlink \
    --verbose \
    --add-modules ALL-MODULE-PATH \
    --strip-debug \
    --no-man-pages \
    --no-header-files \
    --compress=2 \
    --output /customjre

# Stage 3: Create the final application image
FROM alpine:latest

# Set environment variables for the custom JRE
ENV JAVA_HOME=/jre
ENV PATH="${JAVA_HOME}/bin:${PATH}"

# Copy the custom JRE built in the second stage into the application image
COPY --from=corretto-jdk /customjre $JAVA_HOME

# Add a non-root user to enhance security
ARG APPLICATION_USER=appuser
RUN adduser --no-create-home -u 1000 -D $APPLICATION_USER

# Create the application directory and set ownership to the non-root user
RUN mkdir /app && \
    chown -R $APPLICATION_USER /app
USER 1000

# Copy the Spring Boot JAR built in the first stage into the application directory
COPY --from=build /app/target/auth-service-0.0.1-SNAPSHOT.jar /app/app.jar
WORKDIR /app

# Expose the port on which the application will run
EXPOSE 8080

# Specify the entry point to run the Spring Boot application with the custom JRE
ENTRYPOINT [ "/jre/bin/java", "-Dspring.profiles.active=prod", "-jar", "/app/app.jar" ]
