# The first stage caches dependencies
FROM maven:3.9.10-amazoncorretto-21 AS dependencies

WORKDIR /app
COPY pom.xml .

RUN mvn -B -e org.apache.maven.plugins:maven-dependency-plugin:3.8.1:go-offline

# The second stage builds the image
FROM maven:3.9.10-amazoncorretto-21 AS builder

WORKDIR /app
COPY --from=dependencies /root/.m2 /root/.m2
COPY --from=dependencies /app /app
COPY src /app/src

RUN mvn -B -e clean install -DskipTests

# The third stage prepares the runtime environment
FROM openjdk:21-slim

WORKDIR /app
COPY --from=builder /app/target/*.jar /app.jar
EXPOSE 8080
EXPOSE 8878

ENTRYPOINT ["java", "-jar", "/app.jar"]
