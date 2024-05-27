FROM openjdk:17.0.2-jdk-slim-buster
RUN apt-get update && apt-get install -y curl
ARG JAR_FILE=target/*.jar
WORKDIR /app

COPY ${JAR_FILE} /app/eureka-server.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "eureka-server.jar"]
