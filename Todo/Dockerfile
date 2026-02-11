# Use official Maven image with Java 17
FROM maven:3.9-eclipse-temurin-17

# Set working directory
WORKDIR /app

# Copy project files
COPY pom.xml .
COPY src ./src

# Download dependencies (cached layer)
RUN mvn dependency:go-offline

# Expose port
EXPOSE 8988

# Run the application
CMD ["mvn", "spring-boot:run"]
