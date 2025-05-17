FROM eclipse-temurin:21-jre-alpine

ARG JAR_FILE
COPY target/beadando-1.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
