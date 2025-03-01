FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build/libs/test.task-0.0.1-SNAPSHOT.jar /app/test.task.jar

ENTRYPOINT ["java", "-jar", "test.task.jar"]

EXPOSE 8080