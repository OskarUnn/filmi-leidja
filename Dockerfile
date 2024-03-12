FROM openjdk:21-slim
LABEL authors="oskar"

COPY build/libs/*SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]