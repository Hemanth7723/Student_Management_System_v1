# 🎓 Student Management System v1 — Project Instructions

## Overview

This is a **production-ready microservices application** built with:
- **Java 17** + **Spring Boot 3** + **Spring Cloud**
- **Gradle 8** build tool
- **H2** (dev) / **MySQL** (prod) databases
- **Docker + Docker Compose** for containerization
- **Render** for cloud deployment

The system manages full CRUD operations for student records via a microservices architecture.

---

## Architecture

```
Browser / Postman
      │
      ▼
┌──────────────┐  port 8080
│  API Gateway │  ◄──── All external requests
└──────┬───────┘
       │  routes /api/students → lb://student-service
       ▼
┌────────────────┐  port 8081
│ Student Service│  ◄──── CRUD REST API + Business Logic
└──────┬─────────┘
       │
       ▼
┌──────────────┐
│  H2 / MySQL  │  ◄──── Persistence
└──────────────┘

┌──────────────────┐  port 8761
│  Eureka Server   │  ◄──── Service Discovery & Registry
└──────────────────┘
```

## Services

| Service | Port | Role |
|---|---|---|
| `eureka-server` | 8761 | Service discovery & registry |
| `api-gateway` | 8080 | Routing, load balancing, entry point |
| `student-service` | 8081 | Business logic, CRUD API |
| `frontend` | static | HTML UI |

---

## Project Structure

```
Student_Management_System_v1/
├── eureka-server/
│   ├── build.gradle
│   └── src/main/java/com/sms/eurekaserver/
├── api-gateway/
│   ├── build.gradle
│   └── src/main/java/com/sms/apigateway/
├── student-service/
│   ├── build.gradle
│   └── src/main/java/com/sms/studentservice/
│       ├── controller/     ← REST endpoints
│       ├── service/        ← Business logic
│       ├── repository/     ← JPA data access
│       ├── model/          ← Entity classes
│       ├── dto/            ← Data Transfer Objects
│       └── exception/      ← Custom exceptions
├── frontend/
│   └── index.html
├── docker-compose.yml
└── README.md
```

---

## REST API Reference

**Base URL (local):** `http://localhost:8080/api`  
**Base URL (prod):** `https://sms-api-gateway.onrender.com/api`

| Method | Endpoint | Description |
|---|---|---|
| GET | `/students` | List all students |
| GET | `/students/{id}` | Get student by ID |
| GET | `/students/search?q=name` | Search by name |
| POST | `/students` | Create a student |
| PUT | `/students/{id}` | Update a student |
| DELETE | `/students/{id}` | Delete a student |

### Student Entity

```json
{
  "name": "John Doe",
  "age": 21,
  "email": "john.doe@example.com",
  "course": "Computer Science"
}
```

---

## Local Development Startup Order

> ⚠️ Always start services in this exact order:

1. **Eureka Server** → `cd eureka-server && ./gradlew bootRun`
2. **API Gateway** → `cd api-gateway && ./gradlew bootRun`
3. **Student Service** → `cd student-service && ./gradlew bootRun`

**H2 Console:** `http://localhost:8081/h2-console`
- JDBC URL: `jdbc:h2:mem:studentsdb`
- Username: `sa` | Password: *(empty)*

---

## Docker

```bash
# Build all JARs first
./gradlew clean build

# Run full stack
docker compose up --build

# Stop
docker compose down
```

---

## Switching to MySQL (Production)

1. Add to `student-service/build.gradle`:
   ```groovy
   runtimeOnly 'com.mysql:mysql-connector-j'
   ```

2. Update `student-service/src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/studentsdb?createDatabaseIfNotExist=true
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
   spring.jpa.hibernate.ddl-auto=update
   ```

---

## Render Deployment

Deploy services in this order on [render.com](https://render.com):

| Service | Root Dir | Env Var |
|---|---|---|
| `sms-eureka-server` | `eureka-server` | *(none)* |
| `sms-student-service` | `student-service` | `EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=https://sms-eureka-server.onrender.com/eureka/` |
| `sms-api-gateway` | `api-gateway` | `EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=https://sms-eureka-server.onrender.com/eureka/` |

All services use **Docker** environment on Render, branch `main`.

---

## Coding Standards

- Package root: `com.sms.<servicename>`
- Use DTOs for API input/output (never expose entity directly)
- Custom exceptions in `exception/` package
- Service layer owns all business logic — controllers stay thin
- JPA repositories extend `JpaRepository`
- All endpoints behind `/api/` prefix

---

## Planned Future Work

- Replace H2 with MySQL/PostgreSQL persistently
- Add JWT authentication
- CI/CD pipelines (GitHub Actions)
- Monitoring with Prometheus + Grafana
- Additional services (grades, courses, attendance)
