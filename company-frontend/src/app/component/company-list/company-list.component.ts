import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Company } from 'src/app/model/company';
import { CompanyService } from 'src/app/service/company.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-company-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './company-list.component.html',
  styleUrls: ['./company-list.component.css']
})

export class CompanyListComponent implements OnInit {

  companies: Company[] = [];
  selectedCompany?: Company;
  showDetails = false;
  showFinance = false;
  showForm = false;
  formMode: 'add' | 'edit' = 'add';
  formCompany: Company = { name: '', ceoName: '', pointOfContact: '', aboutCompany: '', isActive: true };

  constructor(private companyService: CompanyService) {}

  ngOnInit(): void {
    this.loadCompanies();
  }

  loadCompanies(): void {
    this.companyService.getAllCompanies().subscribe(data => {
      this.companies = data;
    });
  }

  viewDetails(company: Company): void {
    this.selectedCompany = company;
    this.showDetails = true;
    this.showFinance = false;
    this.showForm = false;
  }

  openFinance(company: Company): void {
    this.selectedCompany = company;
    this.showFinance = true;
    this.showDetails = false;
    this.showForm = false;
  }

  openForm(mode: 'add' | 'edit', company?: Company): void {
    this.formMode = mode;
    this.formCompany = company ? { ...company } : { name: '', ceoName: '', pointOfContact: '', aboutCompany: '', isActive: true };
    this.showForm = true;
    this.showDetails = false;
    this.showFinance = false;
  }

  saveCompany(): void {
    if (this.formMode === 'add') {
      this.companyService.addCompany(this.formCompany).subscribe(() => this.loadCompanies());
    } else if (this.formMode === 'edit' && this.formCompany.id) {
      this.companyService.updateCompany(this.formCompany.id, this.formCompany).subscribe(() => this.loadCompanies());
    }
    this.showForm = false;
  }

  deleteCompany(id?: number): void {
    if (!id) return;
    if (confirm('Are you sure you want to delete this company?')) {
      this.companyService.deleteCompany(id).subscribe(() => this.loadCompanies());
    }
  }
}
