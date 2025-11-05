import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, Router, NavigationEnd } from '@angular/router';
import { CompanyService, Company } from '../../services/company.service';
import { AuthService } from '../../services/auth.service';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { TagModule } from 'primeng/tag';
import { TooltipModule } from 'primeng/tooltip';
import { InputTextModule } from 'primeng/inputtext';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-company-list',
  standalone: true,
  imports: [CommonModule, RouterLink, TableModule, ButtonModule, CardModule, TagModule, TooltipModule, InputTextModule],
  templateUrl: './company-list.component.html',
  styleUrls: ['./company-list.component.css']
})
export class CompanyListComponent implements OnInit {
  companies: Company[] = [];
  loading = true;
  error: string | null = null;
  currentUser: any;

  constructor(
    private service: CompanyService,
    private authService: AuthService,
    private router: Router
  ) {
    // Reload data when navigating to this route
    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe(() => {
        this.currentUser = this.authService.getCurrentUser();
        this.load();
      });
  }

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();
    this.load();
  }

  load(): void {
    this.loading = true;
    this.error = null;
    
    const currentUser = this.authService.getCurrentUser();
    
    // Pass role and userId to filter companies
    this.service.getAll(currentUser?.role, currentUser?.id).subscribe(
      (data: Company[]) => {
        this.companies = data;
        this.loading = false;
      },
      (err) => {
        this.error = 'Failed to load companies. Ensure backend is running at http://localhost:8082';
        this.loading = false;
      }
    );
  }

  canEdit(company: Company): boolean {
    if (this.currentUser?.role === 'ADMIN') {
      return true;
    }
    if (this.currentUser?.role === 'COMPANY' && this.currentUser?.companies) {
      return this.currentUser.companies.some((c: { id: number; name: string }) => c.id === company.id);
    }
    return false;
  }

  canView(company: Company): boolean {
    if (this.currentUser?.role === 'ADMIN') {
      return true;
    }
    if (this.currentUser?.role === 'COMPANY' && this.currentUser?.companies) {
      return this.currentUser.companies.some((c: { id: number; name: string }) => c.id === company.id);
    }
    return false;
  }

  goToProjects(company: Company): void {
    // Dummy link for now - will navigate to projects page
    // TODO: Replace with actual project route when available
    alert(`Navigating to projects for ${company.name}...\nThis will redirect to the projects microservice.`);
    
    // Example: You can redirect to an external URL or another route
    // window.open(`http://localhost:3000/projects?companyId=${company.id}`, '_blank');
    // or
    // this.router.navigate(['/projects'], { queryParams: { companyId: company.id } });
  }

  get isAdmin(): boolean {
    return this.currentUser?.role === 'ADMIN';
  }
}

