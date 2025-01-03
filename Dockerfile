FROM maven:3.9-eclipse-temurin-21-alpine AS build

WORKDIR /app

COPY pom.xml .

COPY src ./src

RUN mvn clean install

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

ENV JAVA_OPTS="-Xms512m -Xmx512m"

EXPOSE 3002

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]