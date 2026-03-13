# Student Management System v1

A microservices-based RESTful API built with **Java 17 + Spring Boot 3 +
Spring Cloud + Gradle**.

------------------------------------------------------------------------

## Architecture

    Browser / Postman
            │
            ▼
      ┌──────────────┐    port 8080
      │  API Gateway │  ◄──── All requests enter here
      └──────┬───────┘
             │  (routes /api/students → lb://student-service)
             ▼
      ┌────────────────┐   port 8081
      │ Student Service│  ◄──── CRUD REST API
      └──────┬─────────┘
             │
             ▼
      ┌──────────────┐
      │  H2 Database │  (in-memory; swap to MySQL for production)
      └──────────────┘

      ┌──────────────────┐   port 8761
      │  Eureka Server   │  ◄──── Service Discovery
      └──────────────────┘

------------------------------------------------------------------------

## Prerequisites

  Tool      Version
  --------- ---------
  Java      17+
  Gradle    8+
  Postman   Any

*(Gradle Wrapper is included, so installing Gradle globally is
optional.)*

------------------------------------------------------------------------

## How to Run

Start services in the following order.

### 1. Start Eureka Server

    cd eureka-server
    ./gradlew bootRun

Windows:

    gradlew bootRun

Dashboard:

    http://localhost:8761

------------------------------------------------------------------------

### 2. Start API Gateway

    cd api-gateway
    ./gradlew bootRun

Runs on:

    http://localhost:8080

------------------------------------------------------------------------

### 3. Start Student Service

    cd student-service
    ./gradlew bootRun

Runs on:

    http://localhost:8081

H2 Console:

    http://localhost:8081/h2-console

Connection:

  Property   Value
  ---------- ------------------------
  JDBC URL   jdbc:h2:mem:studentsdb
  Username   sa
  Password   (empty)

------------------------------------------------------------------------

## REST API

Base URL:

    http://localhost:8080/api

  Method   Endpoint                  Description
  -------- ------------------------- -------------------
  GET      /students                 Get all students
  GET      /students/{id}            Get student by id
  GET      /students/search?q=name   Search students
  POST     /students                 Create student
  PUT      /students/{id}            Update student
  DELETE   /students/{id}            Delete student

------------------------------------------------------------------------

## Example Request

POST /api/students

``` json
{
  "name": "John Doe",
  "age": 21,
  "email": "john.doe@example.com",
  "course": "Computer Science"
}
```

------------------------------------------------------------------------

## Switch to MySQL

Add dependency in `build.gradle`

    runtimeOnly 'com.mysql:mysql-connector-j'

Update `application.properties`

    spring.datasource.url=jdbc:mysql://localhost:3306/studentsdb?createDatabaseIfNotExist=true
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.datasource.username=root
    spring.datasource.password=yourpassword

    spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
    spring.jpa.hibernate.ddl-auto=update

------------------------------------------------------------------------

## Project Structure

    student-management-system
    │
    ├── eureka-server
    │   ├── build.gradle
    │   └── src/main/java/com/sms/eurekaserver
    │
    ├── api-gateway
    │   ├── build.gradle
    │   └── src/main/java/com/sms/apigateway
    │
    ├── student-service
    │   ├── build.gradle
    │   └── src/main/java/com/sms/studentservice
    │       ├── controller
    │       ├── service
    │       ├── repository
    │       ├── model
    │       ├── dto
    │       └── exception
    │
    └── frontend
        └── index.html

------------------------------------------------------------------------

## Service Ports

  Service           Port
  ----------------- --------------------
  Eureka Server     8761
  API Gateway       8080
  Student Service   8081
  H2 Console        8081 (/h2-console)
