FROM openjdk:17.0.2-jdk-slim-buster
ARG JAR_FILE=target/*.jar
WORKDIR /app

COPY ${JAR_FILE} /app/api-gateway.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "api-gateway.jar"]
