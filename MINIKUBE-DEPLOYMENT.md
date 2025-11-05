# Minikube Deployment Guide

This guide will help you build Docker images in Minikube and deploy the Company Service application.

## Prerequisites

- Minikube installed and running
- kubectl installed
- Docker installed
- Maven installed (for backend)
- Node.js and npm installed (for frontend)

## Quick Start

### Option 1: Using PowerShell Script (Recommended for Windows)

```powershell
.\deploy-minikube.ps1
```

### Option 2: Manual Deployment

#### Step 1: Start Minikube

```powershell
minikube start
```

#### Step 2: Set Docker Environment to Minikube

```powershell
# PowerShell
& minikube -p minikube docker-env --shell powershell | Invoke-Expression
```

This command configures your terminal to use Minikube's Docker daemon instead of your local Docker.

#### Step 3: Build Backend Image

```powershell
cd demo
mvn clean package -DskipTests
docker build -t company-backend:latest .
cd ..
```

#### Step 4: Build Frontend Image

```powershell
cd frontend-app
docker build -t company-frontend:latest .
cd ..
```

#### Step 5: Deploy to Kubernetes

```powershell
kubectl apply -f k8s/backend-deployment.yaml
kubectl apply -f k8s/frontend-deployment.yaml
```

#### Step 6: Wait for Pods to be Ready

```powershell
kubectl get pods -w
```

Wait until all pods show `Running` status. Press Ctrl+C to stop watching.

#### Step 7: Access the Application

```powershell
# Get the frontend URL
minikube service frontend-service --url

# Or open directly in browser
minikube service frontend-service
```

## Verify Deployment

### Check Pod Status

```powershell
kubectl get pods
```

You should see:
```
NAME                                    READY   STATUS    RESTARTS   AGE
backend-deployment-xxxxxxxxxx-xxxxx     1/1     Running   0          1m
frontend-deployment-xxxxxxxxxx-xxxxx    1/1     Running   0          1m
```

### Check Services

```powershell
kubectl get svc
```

### View Logs

**Backend logs:**
```powershell
kubectl logs -f deployment/backend-deployment
```

**Frontend logs:**
```powershell
kubectl logs -f deployment/frontend-deployment
```

## Troubleshooting

### Pods not starting

Check pod events:
```powershell
kubectl describe pod <pod-name>
```

### Image not found

Make sure Docker environment is set to Minikube:
```powershell
& minikube -p minikube docker-env --shell powershell | Invoke-Expression
```

Then rebuild images.

### Check if images exist in Minikube

```powershell
minikube ssh
docker images | grep company
exit
```

### Backend not connecting

Check backend logs:
```powershell
kubectl logs deployment/backend-deployment
```

### Frontend can't reach backend

The Nginx configuration proxies `/api/` requests to `backend-service:8080`. Verify the backend service exists:
```powershell
kubectl get svc backend-service
```

## Update Application

If you make changes to the code:

1. **Set Minikube Docker environment again** (if new terminal):
   ```powershell
   & minikube -p minikube docker-env --shell powershell | Invoke-Expression
   ```

2. **Rebuild the changed image**:
   ```powershell
   # For backend
   cd demo
   mvn clean package -DskipTests
   docker build -t company-backend:latest .
   cd ..
   
   # For frontend
   cd frontend-app
   docker build -t company-frontend:latest .
   cd ..
   ```

3. **Restart the deployment**:
   ```powershell
   kubectl rollout restart deployment/backend-deployment
   # or
   kubectl rollout restart deployment/frontend-deployment
   ```

## Clean Up

To remove all deployed resources:

```powershell
kubectl delete -f k8s/
```

To stop Minikube:

```powershell
minikube stop
```

To delete Minikube cluster:

```powershell
minikube delete
```

## Architecture

```
┌─────────────────────────────────────────────┐
│           Minikube Cluster                  │
│                                             │
│  ┌────────────────────────────────────┐    │
│  │  Frontend (NodePort 30080)         │    │
│  │  - Angular App                     │    │
│  │  - Nginx Server                    │    │
│  └──────────────┬─────────────────────┘    │
│                 │                           │
│                 │ /api/* proxy              │
│                 ▼                           │
│  ┌────────────────────────────────────┐    │
│  │  Backend (ClusterIP 8080)          │    │
│  │  - Spring Boot                     │    │
│  │  - H2 Database (in-memory)         │    │
│  └────────────────────────────────────┘    │
│                                             │
└─────────────────────────────────────────────┘
```

## Ports

- **Frontend**: Accessible via NodePort on port 30080
- **Backend**: Internal ClusterIP service on port 8080
- **Access URL**: Run `minikube service frontend-service --url` to get the URL

## Notes

- The backend uses an in-memory H2 database, so data will be lost when the pod restarts
- Images use `imagePullPolicy: Never` to use local Minikube images
- For production, you would push images to a registry (Docker Hub, etc.)
