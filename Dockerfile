# Use a base image that includes necessary dependencies
FROM openjdk:17-slim

# Install Python and pip
RUN apt-get update && apt-get install -y \
    python3 \
    python3-pip \
    curl \
    netcat \
    maven \
    nginx \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# Install NVM, Node.js, and npm
ENV NVM_DIR /usr/local/nvm
ENV NODE_VERSION 18.17.1

# Install NVM
RUN mkdir -p $NVM_DIR \
    && curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.1/install.sh | bash

# Install Node.js and npm
SHELL ["/bin/bash", "--login", "-c"]
RUN . $NVM_DIR/nvm.sh \
    && nvm install $NODE_VERSION \
    && nvm alias default $NODE_VERSION \
    && nvm use default

# Update npm to a specific version
#ENV NPM_VERSION 9.7.1
#RUN npm install -g npm@$NPM_VERSION

# Add Node and npm to path so the commands are available
ENV NODE_PATH $NVM_DIR/versions/node/v$NODE_VERSION/lib/node_modules
ENV PATH $NVM_DIR/versions/node/v$NODE_VERSION/bin:$PATH

# Install other dependencies
#RUN apt-get install -y \
#    netcat \
 #   maven \
  #  python3 \
   # python3-pip \
    #nginx \
    #&& apt-get clean \
    #&& rm -rf /var/lib/apt/lists/*

# Copy your application code to the container
COPY . /CityManager-Reinforcement-Learning

# Set the working directory
WORKDIR /CityManager-Reinforcement-Learning

# Python backend setup
WORKDIR /CityManager-Reinforcement-Learning/backAgent
RUN pip3 install -r requirements.txt

# Java backend setup
WORKDIR /CityManager-Reinforcement-Learning/BackSimulation
RUN mvn clean package

# React frontend setup
WORKDIR /CityManager-Reinforcement-Learning/front
RUN npm install

# Nginx setup
RUN cp /CityManager-Reinforcement-Learning/docker_nginx.conf /etc/nginx/nginx.conf
WORKDIR /CityManager-Reinforcement-Learning

# Expose port 80 for Nginx
EXPOSE 80

# Start up script to run the services
COPY docker_launcher.sh /CityManager-Reinforcement-Learning/launcher.sh
RUN chmod +x /CityManager-Reinforcement-Learning/launcher.sh

# Command to run on container start
CMD ["/CityManager-Reinforcement-Learning/launcher.sh"]
