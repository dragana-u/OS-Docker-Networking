FROM debian:11-slim
RUN apt-get update && apt-get install -y nginx
COPY products.txt /var/www/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]