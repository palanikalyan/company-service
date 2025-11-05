import { Routes } from '@angular/router';
import { CompanyListComponent } from './components/company-list/company-list.component';
import { CompanyDetailsComponent } from './components/company-details/company-details.component';
import { CompanyFormComponent } from './components/company-form/company-form.component';
import { LoginComponent } from './components/login/login.component';
import { SignupComponent } from './components/signup/signup.component';
import { AdminDashboardComponent } from './components/admin-dashboard/admin-dashboard.component';

export const routes: Routes = [
	{ path: '', redirectTo: '/login', pathMatch: 'full' },
	{ path: 'login', component: LoginComponent },
	{ path: 'signup', component: SignupComponent },
	{ path: 'admin/dashboard', component: AdminDashboardComponent },
	{ path: 'company/dashboard', component: CompanyListComponent },
	{ path: 'companies/new', component: CompanyFormComponent },
	{ path: 'companies/:id', component: CompanyDetailsComponent }
];
