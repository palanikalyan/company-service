import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface BankDetails {
  id?: number;
  bankName?: string;
  accountNumber?: string;
  ifsc?: string;
  accountHolderName?: string;
}

export interface Company {
  id?: number;
  name: string;
  ceoName?: string;
  pointOfContact?: string;
  aboutCompany?: string;
  isActive?: boolean;
  budget?: number;
  monthlyBudget?: number;
  momGrowthPercent?: number;
  currency?: string;
  bankDetails?: BankDetails;
}

@Injectable({ providedIn: 'root' })
export class CompanyService {
  private base = '/api/companies';

  constructor(private http: HttpClient) {}

  getAll(role?: string, userId?: number): Observable<Company[]> {
    let url = this.base;
    const params: string[] = [];
    
    if (role) {
      params.push(`role=${role}`);
    }
    if (userId) {
      params.push(`userId=${userId}`);
    }
    
    if (params.length > 0) {
      url += '?' + params.join('&');
    }
    
    return this.http.get<Company[]>(url);
  }

  get(id: number): Observable<Company> {
    return this.http.get<Company>(`${this.base}/${id}`);
  }

  create(payload: Partial<Company>, userId?: number): Observable<Company> {
    const url = userId ? `${this.base}?userId=${userId}` : this.base;
    return this.http.post<Company>(url, payload);
  }

  update(id: number, payload: Partial<Company>): Observable<Company> {
    return this.http.put<Company>(`${this.base}/${id}`, payload);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/${id}`);
  }

  getByStatus(isActive: boolean): Observable<Company[]> {
    return this.http.get<Company[]>(`${this.base}/status/${isActive}`);
  }
}

