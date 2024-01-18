#!/bin/bash

check_status() {
    if ! $1; then
        echo "Failed to start $2"
        exit 1
    fi
}

# Start Nginx to serve the React frontend and proxy requests
echo "Starting Nginx..."
service nginx start
#check_status "service nginx start" "Nginx"

# Start the Python backend
echo "Starting Python backend..."
python /app/backAgent/main.py &
#check_status "python /app/backAgent/main.py &" "Python backend"

# Start the Java backend
echo "Starting Java backend..."
java -jar /app/BackSimulation.jar &
#check_status "java -jar /app/BackSimulation.jar &" "Java backend"

# Keep the container running
#tail -f /dev/null
