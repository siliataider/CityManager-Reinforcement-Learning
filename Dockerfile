# Use the official Nginx image as a base
FROM nginx:latest

# Copy your custom nginx.conf into the container
COPY nginx.conf /etc/nginx/nginx.conf

# Expose the port Nginx is listening on
EXPOSE 80

# Start Nginx when the container launches
CMD ["nginx", "-g", "daemon off;"]
