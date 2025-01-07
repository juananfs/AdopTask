# Etapa de construcción
FROM maven:3.9.4-eclipse-temurin-21 AS builder

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:resolve

COPY src ./src
RUN mvn package

# Etapa de ejecución
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app
COPY --from=builder /app/target/adoptask-api-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080
CMD ["java", "-jar", "/app/app.jar"]