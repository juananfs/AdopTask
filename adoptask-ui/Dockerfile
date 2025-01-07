# Etapa de construcción
FROM node:18 AS builder

WORKDIR /app
COPY package*.json .
RUN npm install

COPY src ./src
COPY public ./public
RUN npm run build

# Etapa de ejecución
FROM nginx:stable-alpine

COPY --from=builder /app/build /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]