FROM maven:3-openjdk-17 AS build
COPY . .
RUN  mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
COPY --from=build /target/Careem-System-0.0.1-SNAPSHOT.jar Careem-System.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","Careem-System.jar"]