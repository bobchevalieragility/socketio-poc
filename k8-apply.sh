#!/usr/bin/env bash

# Generate Nginx ConfigMap from file
kubectl create configmap nginx-config --from-file=nginx/nginx.conf

# Deploy frontend app
kubectl apply -f fe/k8/deployment.yaml
kubectl apply -f fe/k8/service.yaml

# Deploy backend app
kubectl apply -f be/k8/deployment.yaml
kubectl apply -f be/k8/service.yaml

# Deploy nginx
#kubectl apply -f nginx/k8/configmap.yaml
kubectl apply -f nginx/k8/deployment.yaml
kubectl apply -f nginx/k8/service.yaml

