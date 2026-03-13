<div align="center">

<h1>🎓 Student Management System v1</h1>

<p>A production-ready <strong>Microservices RESTful API</strong> built with Java 17, Spring Boot 3, Spring Cloud & Gradle</p>

<p>
  <img src="https://img.shields.io/badge/Java-17+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring_Cloud-2022.x-6DB33F?style=for-the-badge&logo=spring&logoColor=white" />
  <img src="https://img.shields.io/badge/Gradle-8+-02303A?style=for-the-badge&logo=gradle&logoColor=white" />
  <img src="https://img.shields.io/badge/Docker-Ready-2496ED?style=for-the-badge&logo=docker&logoColor=white" />
</p>

<p>
  <img src="https://img.shields.io/badge/H2-In--Memory_DB-1C1C1C?style=for-the-badge&logo=databricks&logoColor=white" />
  <img src="https://img.shields.io/badge/MySQL-Compatible-4479A1?style=for-the-badge&logo=mysql&logoColor=white" />
  <img src="https://img.shields.io/badge/Deployed_on-Render-46E3B7?style=for-the-badge&logo=render&logoColor=white" />
</p>

</div>

---

## 📌 Overview

A scalable, cloud-ready **Student Management System** built using a microservices architecture. The system manages full CRUD operations for student records and is designed with service discovery, API routing, and containerization built in — ready for both local development and cloud deployment.

---

## 🏗️ Architecture

```
Browser / Postman
      │
      ▼
┌──────────────┐  port 8080
│  API Gateway │ ◄──── All external requests enter here
└──────┬───────┘
       │   (routes /api/students → lb://student-service)
       ▼
┌────────────────┐  port 8081
│ Student Service│ ◄──── CRUD REST API
└──────┬─────────┘
       │
       ▼
┌──────────────┐
│  H2 Database │  (in-memory; swap to MySQL for production)
└──────────────┘

┌──────────────────┐  port 8761
│  Eureka Server   │ ◄──── Service Discovery
└──────────────────┘
```

| Service | Role | Port |
|---|---|---|
| 🔍 Eureka Server | Service Discovery & Registry | `8761` |
| 🌐 API Gateway | Request Routing & Load Balancing | `8080` |
| 🎓 Student Service | Business Logic + CRUD API | `8081` |
| 🗄️ H2 Console | In-Memory DB UI | `8081/h2-console` |

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3 |
| Microservices | Spring Cloud (Eureka, Gateway) |
| Build Tool | Gradle 8 |
| Database | H2 (dev) / MySQL (prod) |
| Containerization | Docker + Docker Compose |
| Deployment | Render |

---

## ✅ Prerequisites

| Tool | Version |
|---|---|
| ☕ Java | 17+ |
| 🐘 Gradle | 8+ *(or use included Gradle Wrapper)* |
| 📮 Postman | Any |
| 🐳 Docker | For containerized runs |

> **Note:** The Gradle Wrapper is bundled — no global Gradle install needed.

---

## 🚀 How to Run (Local)

Start services **in this order:**

### 1️⃣ Eureka Server
```bash
cd eureka-server
./gradlew bootRun          # macOS / Linux
gradlew bootRun            # Windows
```
🌐 Dashboard: http://localhost:8761

---

### 2️⃣ API Gateway
```bash
cd api-gateway
./gradlew bootRun
```
🌐 Runs on: http://localhost:8080

---

### 3️⃣ Student Service
```bash
cd student-service
./gradlew bootRun
```
🌐 API: http://localhost:8081  
🗄️ H2 Console: http://localhost:8081/h2-console

**H2 Connection Details:**

| Property | Value |
|---|---|
| JDBC URL | `jdbc:h2:mem:studentsdb` |
| Username | `sa` |
| Password | *(leave empty)* |

---

## 📡 REST API Reference

**Base URL:** `http://localhost:8080/api`

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/students` | Get all students |
| `GET` | `/students/{id}` | Get student by ID |
| `GET` | `/students/search?q=name` | Search students by name |
| `POST` | `/students` | Create a new student |
| `PUT` | `/students/{id}` | Update a student |
| `DELETE` | `/students/{id}` | Delete a student |

### 📝 Example Request

**POST** `/api/students`

```json
{
  "name": "John Doe",
  "age": 21,
  "email": "john.doe@example.com",
  "course": "Computer Science"
}
```

---

## 🔄 Switch to MySQL (Production)

**1. Add dependency in `build.gradle`:**
```groovy
runtimeOnly 'com.mysql:mysql-connector-j'
```

**2. Update `application.properties`:**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/studentsdb?createDatabaseIfNotExist=true
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update
```

---

## 🐳 Docker Setup

### Build JAR Files
```bash
./gradlew clean build
# JAR generated in: build/libs/
```

### Docker Compose (Recommended)
```yaml
version: "3.8"
services:
  # eureka-server, api-gateway, student-service
```

```bash
docker compose up --build
```

---

## ☁️ Deploying to Render

### Step 1 — Push to GitHub
Push your project repository to GitHub (e.g., `Student_Management_System_v1`).

### Step 2 — Create a Render Account
Visit [render.com](https://render.com) and log in with GitHub.

### Step 3 — Deploy Each Service

**🔍 Eureka Server**
| Config | Value |
|---|---|
| Name | `sms-eureka-server` |
| Environment | Docker |
| Root Directory | `eureka-server` |
| Branch | `main` |

**🎓 Student Service**
| Config | Value |
|---|---|
| Name | `sms-student-service` |
| Environment | Docker |
| Root Directory | `student-service` |
| Env Variable | `EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=https://sms-eureka-server.onrender.com/eureka/` |

**🌐 API Gateway**
| Config | Value |
|---|---|
| Name | `sms-api-gateway` |
| Environment | Docker |
| Root Directory | `api-gateway` |
| Env Variable | `EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=https://sms-eureka-server.onrender.com/eureka/` |

### 🌍 Live API URL
```
https://sms-api-gateway.onrender.com/api/students
```

---

## 📁 Project Structure

```
student-management-system/
│
├── 📦 eureka-server/
│   ├── build.gradle
│   └── src/main/java/com/sms/eurekaserver/
│
├── 📦 api-gateway/
│   ├── build.gradle
│   └── src/main/java/com/sms/apigateway/
│
├── 📦 student-service/
│   ├── build.gradle
│   └── src/main/java/com/sms/studentservice/
│       ├── controller/
│       ├── service/
│       ├── repository/
│       ├── model/
│       ├── dto/
│       └── exception/
│
└── 🖥️ frontend/
    └── index.html
```

---

## 🔮 Future Improvements

- [ ] 🗄️ Replace H2 with MySQL / PostgreSQL
- [ ] 🔐 Add Authentication (JWT)
- [ ] ⚙️ Implement CI/CD Pipelines
- [ ] 📊 Add Monitoring (Prometheus + Grafana)

---

<div align="center">

Made with ❤️ using Java · Spring Boot · Spring Cloud

</div>
