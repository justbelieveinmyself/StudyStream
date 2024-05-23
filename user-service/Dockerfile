FROM openjdk:17.0.2-jdk-slim-buster
ARG JAR_FILE=target/*.jar
WORKDIR /app

COPY ${JAR_FILE} /app/user-service.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "user-service.jar"]
