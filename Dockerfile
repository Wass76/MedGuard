
FROM openjdk:21-jdk-slim

ENV JAVA_HOME=/usr/local/openjdk-17

WORKDIR /app

COPY target/MedGuard-0.0.1-SNAPSHOT.jar /app/MedGuard.jar

EXPOSE 3001

ENTRYPOINT ["java", "-jar", "/app/MedGuard.jar", "mvn spring-boot:run"]