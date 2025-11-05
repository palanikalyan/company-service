## Multi-step build is handled in CI; this Dockerfile expects the built jar in target/
FROM eclipse-temurin:17-jre-jammy

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "/app.jar"]
