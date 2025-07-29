#!/usr/bin/env bash

# Delete frontend app
kubectl delete service frontend
kubectl delete deployment fe-app-deployment

# Delete backend app
kubectl delete service backend
kubectl delete deployment be-app-deployment

# Delete nginx
kubectl delete service gateway
kubectl delete deployment nginx-deployment
kubectl delete configmap nginx-config

