#!/bin/bash
docker build -t tvilarim/eng-fg-image:latest ./frontend
docker push tvilarim/eng-fg-image:latest

docker build -t tvilarim/eng-be-image:latest ./backend
docker push tvilarim/eng-be-image:latest

kubectl apply -f k8s/postgres-deployment.yaml
kubectl apply -f k8s/backend-deployment.yaml
kubectl apply -f k8s/frontend-deployment.yaml
kubectl apply -f k8s/ingress.yaml