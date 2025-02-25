# Use Node.js for building the React app
FROM node:18 as build-stage

# Set working directory
WORKDIR /app

# Copy package.json and package-lock.json
COPY package.json package-lock.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application files
COPY . ./

# Build the application for production
RUN npm run build

# Use Nginx to serve the built React app
FROM nginx:stable-alpine as production-stage

# Copy the built files to Nginx’s default public directory
COPY --from=build-stage /app/dist /usr/share/nginx/html

# Expose port 80 for the application
EXPOSE 80

# Start Nginx server
CMD ["nginx", "-g", "daemon off;"]
