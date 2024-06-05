# Etapa de compilación
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Comando para ejecutar la aplicación Spring Boot
ENTRYPOINT ["java","-jar","app.jar"]
