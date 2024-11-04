FROM openjdk:21-slim

WORKDIR /app

COPY target/*.jar /app.jar

EXPOSE 8085

CMD ["java", "-jar", "/app.jar"]
