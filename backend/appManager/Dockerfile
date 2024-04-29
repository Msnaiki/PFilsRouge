# Start with a base image containing Java runtime
FROM openjdk:21-jdk-slim as build

# The application's jar file
ARG JAR_FILE=target/*.jar

# Add the application's jar to the container
COPY ${JAR_FILE} app.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java","-jar","/app.jar"]