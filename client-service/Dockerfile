FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/client-service-0.0.1.jar client-service.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "client-service.jar"]