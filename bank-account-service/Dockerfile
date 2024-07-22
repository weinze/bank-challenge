FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/bank-account-service-0.0.1.jar bank-account-service.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "bank-account-service.jar"]
