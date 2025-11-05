#!/bin/bash

echo "======================================"
echo "Deploying to Minikube"
echo "======================================"

# Set Minikube's Docker environment
echo "Setting up Minikube Docker environment..."
eval $(minikube docker-env)

# Build Backend Docker Image
echo ""
echo "Building Backend Docker image..."
cd demo
mvn clean package -DskipTests
docker build -t company-backend:latest .
cd ..

# Build Frontend Docker Image
echo ""
echo "Building Frontend Docker image..."
cd frontend-app
docker build -t company-frontend:latest .
cd ..

# Apply Kubernetes manifests
echo ""
echo "Applying Kubernetes deployments..."
kubectl apply -f k8s/backend-deployment.yaml
kubectl apply -f k8s/frontend-deployment.yaml

# Wait for deployments to be ready
echo ""
echo "Waiting for deployments to be ready..."
kubectl wait --for=condition=available --timeout=300s deployment/backend-deployment
kubectl wait --for=condition=available --timeout=300s deployment/frontend-deployment

# Get service URLs
echo ""
echo "======================================"
echo "Deployment Complete!"
echo "======================================"
echo ""
echo "Backend Service:"
kubectl get svc backend-service
echo ""
echo "Frontend Service:"
kubectl get svc frontend-service
echo ""
echo "Access the application at:"
minikube service frontend-service --url
echo ""
echo "To view all resources:"
echo "  kubectl get all"
echo ""
echo "To view logs:"
echo "  kubectl logs -f deployment/backend-deployment"
echo "  kubectl logs -f deployment/frontend-deployment"
