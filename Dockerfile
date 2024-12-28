# Build stage
FROM maven:3.9-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Download dependencies and build the application
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Add a non-root user
RUN addgroup -S spring && adduser -S spring -G spring
USER spring

# Copy the built jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Configure JVM options
ENV JAVA_OPTS="-Xms512m -Xmx512m"

EXPOSE 3002
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]