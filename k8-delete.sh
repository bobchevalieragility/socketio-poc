#!/usr/bin/env bash

# Delete frontend app
kubectl delete service fe-svc
kubectl delete deployment fe-app-deployment

# Delete backend app
kubectl delete service be-svc-internal
kubectl delete service be-svc-external
kubectl delete deployment be-app-deployment

# Delete nginx
kubectl delete service nginx-svc
kubectl delete deployment nginx-deployment
kubectl delete configmap nginx-config

