import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Company } from '../model/company';

@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  private baseUrl = 'http://localhost:8082/api/companies'; // adjust if needed

  constructor(private http: HttpClient) {}

  // Add a new company
  addCompany(company: Company): Observable<Company> {
    return this.http.post<Company>(this.baseUrl, company);
  }

  // Update company by ID
  updateCompany(id: number, company: Company): Observable<Company> {
    return this.http.put<Company>(`${this.baseUrl}/${id}`, company);
  }

  // Delete company by ID
  deleteCompany(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  // Get all companies
  getAllCompanies(): Observable<Company[]> {
    return this.http.get<Company[]>(this.baseUrl);
  }

  // Get companies by status
  getCompaniesByStatus(isActive: boolean): Observable<Company[]> {
    return this.http.get<Company[]>(`${this.baseUrl}/status/${isActive}`);
  }

  // Get all company details
  getAllCompanyDetails(): Observable<Company[]> {
    return this.http.get<Company[]>(`${this.baseUrl}/details`);
  }
}
