#!/bin/bash

# Start Nginx reverse proxy container
cp ./nginx.conf /tmp/
sudo docker run --name citymanager-nginx-container --network host -v /tmp/nginx.conf:/etc/nginx/nginx.conf:ro --rm nginx &

# Install python requirements
cd backAgent
pip install -r requirements.txt
python main.py &

# Start the java backend
cd ../BackSimulation
mvn clean package
java -jar target/BackSimulation-0.0.1-SNAPSHOT.jar &

# Start the react front-end
cd ../front
npm install
npm run dev &

# Done
echo "READY !"
