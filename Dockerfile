FROM maven:3.9-eclipse-temurin-21-alpine AS build
WORKDIR /app

COPY ./pom.xml /app/pom.xml
COPY ./src/main/java/com/my_agenda_user_api/MyAgendaUserApiApplication.java \
    /app/src/main/java/com/my_agenda_user_api/MyAgendaUserApiApplication.java

RUN mvn -f /app/pom.xml clean package

COPY . /app

FROM eclipse-temurin:21-jdk-alpine
EXPOSE 3002
COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]