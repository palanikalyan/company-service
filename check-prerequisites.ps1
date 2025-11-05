# Pre-deployment checks for Minikube
Write-Host "======================================" -ForegroundColor Cyan
Write-Host "Minikube Pre-Deployment Checks" -ForegroundColor Cyan
Write-Host "======================================" -ForegroundColor Cyan

$allChecksPass = $true

# Check if Minikube is installed
Write-Host "`nChecking Minikube installation..." -ForegroundColor Yellow
try {
    $minikubeVersion = minikube version --short 2>$null
    Write-Host "✓ Minikube installed: $minikubeVersion" -ForegroundColor Green
} catch {
    Write-Host "✗ Minikube not found. Please install Minikube." -ForegroundColor Red
    $allChecksPass = $false
}

# Check if kubectl is installed
Write-Host "`nChecking kubectl installation..." -ForegroundColor Yellow
try {
    $kubectlVersion = kubectl version --client --short 2>$null
    Write-Host "✓ kubectl installed" -ForegroundColor Green
} catch {
    Write-Host "✗ kubectl not found. Please install kubectl." -ForegroundColor Red
    $allChecksPass = $false
}

# Check if Docker is installed
Write-Host "`nChecking Docker installation..." -ForegroundColor Yellow
try {
    $dockerVersion = docker --version 2>$null
    Write-Host "✓ Docker installed: $dockerVersion" -ForegroundColor Green
} catch {
    Write-Host "✗ Docker not found. Please install Docker." -ForegroundColor Red
    $allChecksPass = $false
}

# Check if Maven is installed
Write-Host "`nChecking Maven installation..." -ForegroundColor Yellow
try {
    $mavenVersion = mvn --version 2>$null | Select-Object -First 1
    Write-Host "✓ Maven installed: $mavenVersion" -ForegroundColor Green
} catch {
    Write-Host "✗ Maven not found. Please install Maven." -ForegroundColor Red
    $allChecksPass = $false
}

# Check if Minikube is running
Write-Host "`nChecking Minikube status..." -ForegroundColor Yellow
try {
    $minikubeStatus = minikube status 2>$null
    if ($minikubeStatus -match "Running") {
        Write-Host "✓ Minikube is running" -ForegroundColor Green
        
        # Show Minikube details
        Write-Host "`nMinikube Details:" -ForegroundColor Cyan
        minikube profile list
        
    } else {
        Write-Host "⚠ Minikube is not running" -ForegroundColor Yellow
        Write-Host "  Start Minikube with: minikube start" -ForegroundColor White
        $allChecksPass = $false
    }
} catch {
    Write-Host "⚠ Could not get Minikube status" -ForegroundColor Yellow
    Write-Host "  Start Minikube with: minikube start" -ForegroundColor White
    $allChecksPass = $false
}

Write-Host "`n======================================" -ForegroundColor Cyan

if ($allChecksPass) {
    Write-Host "All checks passed! ✓" -ForegroundColor Green
    Write-Host "`nYou can now run the deployment script:" -ForegroundColor Yellow
    Write-Host "  .\deploy-minikube.ps1" -ForegroundColor White
} else {
    Write-Host "Some checks failed! ✗" -ForegroundColor Red
    Write-Host "`nPlease fix the issues above before deploying." -ForegroundColor Yellow
}

Write-Host "======================================" -ForegroundColor Cyan

