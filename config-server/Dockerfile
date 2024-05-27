FROM openjdk:17.0.2-jdk-slim-buster
RUN apt-get update && apt-get install -y curl
ARG JAR_FILE=target/*.jar
WORKDIR /app

COPY ${JAR_FILE} /app/config-server.jar

EXPOSE 8888

ENTRYPOINT ["java", "-jar", "config-server.jar"]
