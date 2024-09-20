# Use an official Java runtime as a parent image
FROM openjdk:8-jdk-alpine

# Set the working directory in container
WORKDIR /app

# Copy the jar file into the container at /app
COPY build/libs/demo-spring-active-mq-example-1.0-SNAPSHOT.jar /app/spring-app.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the jar file
CMD ["java", "-jar", "/app/spring-app.jar"]