# my-agenda-user-api

## Description
This is a Spring Boot application for managing user data.

## Project Structure

```
my-agenda-user-api/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── my_agenda_user_api/
│   │   │           ├── controller/
│   │   │           ├── exception/
│   │   │           ├── model/
│   │   │           └── service/
│   │   └── resources/
│   └── test/
├── pom.xml
└── README.md
```

## Endpoints
- `GET /users/{id}`: Retrieve a user by ID.
- `POST /users`: Create a new user.
- `DELETE /users/{id}`: Delete a user by ID.

## Requirements
- Java 21
- Maven 3.6+
- Docker

## How to Run

### Using Maven
1. Navigate to the project directory.
2. Run the application:
   ```sh
   mvn spring-boot:run
   ```

### Using Docker
1. Build the Docker image:
   ```sh
   docker build -t my-agenda-user-api .
   ```
2. Run the Docker container:
   ```sh
   docker run -p 3002:3002 my-agenda-user-api
   ```

## Links
- [Actuator](http://localhost:3002/actuator)
- [Swagger](http://localhost:3002/swagger-ui/index.html)
- [Host](http://localhost:3002)
```