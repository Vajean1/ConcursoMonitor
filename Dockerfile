FROM openjdk:24-slim

WORKDIR /app

COPY target/concurso_monitor-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]
