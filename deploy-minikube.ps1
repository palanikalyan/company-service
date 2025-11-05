# Deployment script for Minikube on Windows
Write-Host "======================================" -ForegroundColor Cyan
Write-Host "Deploying to Minikube" -ForegroundColor Cyan
Write-Host "======================================" -ForegroundColor Cyan

# Set Minikube's Docker environment
Write-Host "`nSetting up Minikube Docker environment..." -ForegroundColor Yellow
& minikube -p minikube docker-env --shell powershell | Invoke-Expression

# Build Backend Docker Image
Write-Host "`nBuilding Backend Docker image..." -ForegroundColor Yellow
Push-Location demo
mvn clean package -DskipTests
if ($LASTEXITCODE -ne 0) {
    Write-Host "Maven build failed!" -ForegroundColor Red
    Pop-Location
    exit 1
}
docker build -t company-backend:latest .
if ($LASTEXITCODE -ne 0) {
    Write-Host "Backend Docker build failed!" -ForegroundColor Red
    Pop-Location
    exit 1
}
Pop-Location

# Build Frontend Docker Image
Write-Host "`nBuilding Frontend Docker image..." -ForegroundColor Yellow
Push-Location frontend-app
docker build -t company-frontend:latest .
if ($LASTEXITCODE -ne 0) {
    Write-Host "Frontend Docker build failed!" -ForegroundColor Red
    Pop-Location
    exit 1
}
Pop-Location

# Apply Kubernetes manifests
Write-Host "`nApplying Kubernetes deployments..." -ForegroundColor Yellow
kubectl apply -f k8s/backend-deployment.yaml
kubectl apply -f k8s/frontend-deployment.yaml

# Wait for deployments to be ready
Write-Host "`nWaiting for deployments to be ready..." -ForegroundColor Yellow
kubectl wait --for=condition=available --timeout=300s deployment/backend-deployment
kubectl wait --for=condition=available --timeout=300s deployment/frontend-deployment

# Get service URLs
Write-Host "`n======================================" -ForegroundColor Cyan
Write-Host "Deployment Complete!" -ForegroundColor Green
Write-Host "======================================" -ForegroundColor Cyan

Write-Host "`nBackend Service:" -ForegroundColor Yellow
kubectl get svc backend-service

Write-Host "`nFrontend Service:" -ForegroundColor Yellow
kubectl get svc frontend-service

Write-Host "`nAccess the application at:" -ForegroundColor Yellow
$frontendUrl = minikube service frontend-service --url
Write-Host $frontendUrl -ForegroundColor Green

Write-Host "`n======================================" -ForegroundColor Cyan
Write-Host "Useful Commands:" -ForegroundColor Cyan
Write-Host "======================================" -ForegroundColor Cyan
Write-Host "View all resources:"
Write-Host "  kubectl get all" -ForegroundColor White
Write-Host "`nView backend logs:"
Write-Host "  kubectl logs -f deployment/backend-deployment" -ForegroundColor White
Write-Host "`nView frontend logs:"
Write-Host "  kubectl logs -f deployment/frontend-deployment" -ForegroundColor White
Write-Host "`nOpen frontend in browser:"
Write-Host "  minikube service frontend-service" -ForegroundColor White
Write-Host "`nDelete all resources:"
Write-Host "  kubectl delete -f k8s/" -ForegroundColor White
