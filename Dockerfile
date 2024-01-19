# Stage 1: Build the Java application
FROM maven:3.8.4-openjdk-17-slim AS java-builder
COPY BackSimulation /BackSimulation
WORKDIR /BackSimulation
RUN mvn clean package

# Stage 2: Build the React application
FROM node:16 AS react-builder
COPY front /front
WORKDIR /front
RUN npm install
RUN npm run build

# Stage 3: Build the React application
FROM python:3.10

# Stage 4: Install Nginx and Java
RUN apt-get update && apt-get install -y nginx

# Stage 5: Install JRE 17
RUN apt-get install -y openjdk-17-jre-headless

# Stage 6: Copy the built Java and React applications
COPY --from=java-builder /BackSimulation/target/BackSimulation-0.0.1-SNAPSHOT.jar /app/BackSimulation.jar
COPY --from=react-builder /front/dist /app/front

# Stage 7: Copy the Python application
COPY backAgent /app/backAgent

# Stage 8: Copy Nginx configuration
COPY docker_nginx.conf /etc/nginx/nginx.conf

# Stage 9: Install Python dependencies
WORKDIR /app/backAgent
RUN pip install -r requirements.txt

# Stage 10: Copy the launcher script
COPY docker_launcher.sh /app/launcher.sh
RUN chmod +x /app/launcher.sh

# Stage 11: Expose necessary ports
EXPOSE 80 443

# Command to run the launcher script
CMD ["/app/launcher.sh"]
