# Etapa 1: Compilação do Angular
FROM node:18 AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build --prod

# Etapa 2: Servir o conteúdo estático com Nginx
FROM nginx:alpine
COPY --from=build /app/dist/frontend /usr/share/nginx/html