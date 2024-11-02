FROM maven:latest AS build
COPY . /app
WORKDIR /app
RUN mvn clean package
FROM openjdk:21-slim
COPY --from=build /app/target/*.jar /app.jar
CMD ["java", "-jar", "/app.jar"]