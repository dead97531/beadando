FROM maven:3.9.4-eclipse-temurin-21 AS build

COPY . /app
WORKDIR /app
RUN mvn clean package

FROM eclipse-temurin:21-jre
COPY --from=build /app/target/beadando-1.0-SNAPSHOT.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]