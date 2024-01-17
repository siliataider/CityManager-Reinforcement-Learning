# Use a base image with Java (specify the Java version you need)
FROM openjdk:17-jdk-alpine

# Copy the jar file into the image
COPY BackSimulation/target/BackSimulation-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your app runs on
EXPOSE 8080

# Command to run your app
ENTRYPOINT ["java","-jar","/app.jar"]
