FROM maven:3.9.9-ibmjava AS build
COPY . /app
WORKDIR /app
RUN mvn dependency:purge-local-repository
RUN mvn clean verify -U
FROM openjdk:21-slim
COPY --from=build /app/target/*.jar /app.jar
CMD ["java", "-jar", "/app.jar"]
