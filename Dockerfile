# Stage 1: Clone, Build, and Generate JAR
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Clone repo
ARG REPO_URL=https://github.com/Devendra274/URL-Shortener.git
RUN git clone ${REPO_URL} .

# Build the application and generate JAR file
RUN mvn clean package -DskipTests

# Stage 2: Run Application
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy JAR from build stage
COPY --from=build /app/target/url-shortener-0.0.1-SNAPSHOT.jar url-shortener.jar

# Expose the port the application runs on
EXPOSE 8080

# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "/app/url-shortener.jar"]