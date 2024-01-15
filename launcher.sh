#!/bin/bash

# Start Nginx reverse proxy container
cp ./nginx.conf /tmp/
sudo docker run --name citymanager-nginx-container --network host -v /tmp/nginx.conf:/etc/nginx/nginx.conf:ro --rm nginx &

# To kill all dockers : docker stop $(docker ps -a -q)

# Start the react front-end
cd front
npm install
npm run dev &

echo "READY !"