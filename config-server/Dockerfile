FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/config-server-0.0.1-SNAPSHOT.jar /app/config-server.jar

EXPOSE 8888

ENTRYPOINT ["java", "-jar", "config-server.jar"]
