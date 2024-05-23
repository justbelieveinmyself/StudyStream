FROM openjdk:17.0.2-jdk-slim-buster
ARG JAR_FILE=target/*.jar
WORKDIR /app

COPY ${JAR_FILE} /app/course-service.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "course-service.jar"]
