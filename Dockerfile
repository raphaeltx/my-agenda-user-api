# Build stage
FROM maven:3.9-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

RUN addgroup -S spring && adduser -S spring -G spring
USER spring

COPY --from=build /app/target/*.jar app.jar

# Configure JVM options
ENV JAVA_OPTS="-Xms512m -Xmx512m"

EXPOSE 3002
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]