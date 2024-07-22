FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/bank-account-service.jar bank-account-service.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "bank-account-service.jar"]
