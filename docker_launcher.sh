#!/bin/bash

# Start Nginx reverse proxy
echo "Starting Nginx..."
nginx &

# Start the Python backend
echo "Starting Python backend..."
cd /CityManager-Reinforcement-Learning/backAgent
python3 main.py &

# Wait for the Python backend to be ready
echo "Waiting for Python backend to be ready..."
while ! nc -z localhost 8765; do   
  sleep 1
done
echo "Python backend is ready."

# Start the Java backend
echo "Starting Java backend..."
cd /CityManager-Reinforcement-Learning/BackSimulation
java -jar target/BackSimulation-0.0.1-SNAPSHOT.jar &

# Start the React front-end
echo "Starting Node backend..."
cd /CityManager-Reinforcement-Learning/front
npm run dev

# Keep the container running
tail -f /dev/null

