import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../services/auth.service';
import { CompanyService } from '../../services/company.service';

interface User {
  id: number;
  username: string;
  email: string;
  role: string;
}

interface Company {
  id: number;
  name: string;
  aboutCompany: string;
  emailId?: string;
  mobileNo?: string;
  isActive: boolean;
  ceoName?: string;
  pointOfContact?: string;
  budget?: number;
  currency?: string;
}

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css'
})
export class AdminDashboardComponent implements OnInit {
  users: User[] = [];
  companies: Company[] = [];
  currentUser: any;
  activeTab: 'users' | 'companies' = 'companies';

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private companyService: CompanyService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();
    if (!this.authService.isAdmin()) {
      this.router.navigate(['/login']);
      return;
    }
    this.loadUsers();
    this.loadCompanies();
  }

  loadUsers(): void {
    this.http.get<User[]>('http://localhost:8082/api/admin/users').subscribe({
      next: (data) => this.users = data,
      error: (err) => console.error('Error loading users:', err)
    });
  }

  loadCompanies(): void {
    this.http.get<Company[]>('http://localhost:8082/api/admin/companies').subscribe({
      next: (data) => this.companies = data,
      error: (err) => console.error('Error loading companies:', err)
    });
  }

  deleteUser(id: number): void {
    if (confirm('Are you sure you want to delete this user?')) {
      this.http.delete(`http://localhost:8082/api/admin/users/${id}`).subscribe({
        next: () => this.loadUsers(),
        error: (err) => console.error('Error deleting user:', err)
      });
    }
  }

  deleteCompany(id: number): void {
    if (confirm('Are you sure you want to delete this company?')) {
      this.http.delete(`http://localhost:8082/api/admin/companies/${id}`).subscribe({
        next: () => this.loadCompanies(),
        error: (err) => console.error('Error deleting company:', err)
      });
    }
  }

  toggleCompanyStatus(id: number, activate: boolean): void {
    const endpoint = activate ? 'activate' : 'deactivate';
    this.http.put(`http://localhost:8082/api/admin/companies/${id}/${endpoint}`, {}).subscribe({
      next: () => this.loadCompanies(),
      error: (err) => console.error('Error toggling company status:', err)
    });
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
