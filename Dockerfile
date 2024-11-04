FROM maven:3.9.9-eclipse-temurin AS build
COPY . /app
WORKDIR /app
RUN mvn -B package --file pom.xml
FROM openjdk:21-slim
COPY --from=build /app/target/*.jar /app.jar
CMD ["java", "-jar", "/app.jar"]