# Use the official Node image to build your application
FROM node:14 as build
WORKDIR /app
COPY package.json package-lock.json* ./
RUN npm install
COPY . .
RUN npm run build

# Use Nginx to serve your application
FROM nginx:alpine
COPY --from=build /app/dist/* /usr/share/nginx/html/
COPY ./nginx.conf /etc/nginx/nginx.conf