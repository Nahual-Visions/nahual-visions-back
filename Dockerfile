FROM openjdk:21-slim

WORKDIR /app

COPY *.jar /app.jar

EXPOSE 8085

CMD ["java", "-jar", "/app.jar"]
