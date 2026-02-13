# Use official Maven image with Java 17
FROM maven:3.9-eclipse-temurin-17

# Set working directory
WORKDIR /app

# Copy project files
COPY pom.xml .
COPY src ./src

# Download dependencies (cached layer)
RUN mvn dependency:go-offline -B

# Expose port (update if needed)
EXPOSE 8083

# Run the application
CMD ["mvn", "spring-boot:run"]
