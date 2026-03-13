# FROM eclipse-temurin:17-jdk-alpine

# WORKDIR /app

# COPY build/libs/api-gateway-1.0.0.jar app.jar

# EXPOSE 8080

# ENTRYPOINT ["java","-jar","app.jar"]

# Stage 1: Build the application
FROM gradle:8.5-jdk17-alpine AS builder

WORKDIR /app

COPY . .

RUN gradle build -x test

# Stage 2: Run the application
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8761

ENTRYPOINT ["java","-jar","app.jar"]
