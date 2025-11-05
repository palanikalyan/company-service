import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CompanyService, Company } from '../../services/company.service';
import { AuthService } from '../../services/auth.service';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { CardModule } from 'primeng/card';
import { CheckboxModule } from 'primeng/checkbox';
import { TooltipModule } from 'primeng/tooltip';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { TabViewModule } from 'primeng/tabview';

@Component({
  selector: 'app-company-details',
  standalone: true,
  imports: [
    CommonModule, 
    FormsModule, 
    ReactiveFormsModule, 
    RouterLink, 
    ButtonModule, 
    InputTextModule, 
    CardModule, 
    CheckboxModule, 
    TooltipModule,
    InputTextareaModule,
    TabViewModule
  ],
  templateUrl: './company-details.component.html',
  styleUrls: ['./company-details.component.css']
})
export class CompanyDetailsComponent implements OnInit {
  company?: Company;
  editMode = false;
  bankEditMode = false;
  editForm!: FormGroup;
  bankForm!: FormGroup;
  submitting = false;
  bankSubmitting = false;
  successMessage: string | null = null;
  errorMessage: string | null = null;
  bankSuccessMessage: string | null = null;
  bankErrorMessage: string | null = null;
  spocs: string[] = [''];  // Array for multiple SPOCs
  spocPhones: string[] = [''];  // Array for SPOC phone numbers
  spocEmails: string[] = [''];  // Array for SPOC emails
  spocDesignations: string[] = [''];  // Array for SPOC designations
  
  constructor(
    private route: ActivatedRoute,
    private svc: CompanyService,
    private authService: AuthService,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.editForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2)]],
      ceoName: ['', [Validators.minLength(2)]],
      aboutCompany: ['', [Validators.maxLength(500)]],
      budget: [null, [Validators.min(0)]],
      monthlyBudget: [null, [Validators.min(0)]],
      momGrowthPercent: [null],
      currency: ['USD'],
      isActive: [true]
    });

    this.bankForm = this.fb.group({
      bankName: ['', [Validators.required]],
      accountNumber: ['', [Validators.required]],
      ifsc: ['', [Validators.required]],
      accountHolderName: ['', [Validators.required]]
    });
  }
  
  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    const currentUser = this.authService.getCurrentUser();
    
    if (id) {
      this.svc.get(id).subscribe({
        next: (c: Company) => {
          // Check if user has permission to view this company
          if (currentUser?.role === 'COMPANY') {
            const userCompanies = (currentUser as any)?.companies || [];
            const hasAccess = userCompanies.some((company: any) => company.id === id);
            if (!hasAccess) {
              alert('You do not have permission to view this company');
              this.router.navigate(['/company/dashboard']);
              return;
            }
          }
          this.company = c;
          this.populateForm();
        },
        error: (err) => {
          console.error('Error loading company:', err);
          this.router.navigate(['/company/dashboard']);
        }
      });
    }
  }

  populateForm(): void {
    if (this.company) {
      // Parse SPOCs and related data from comma-separated strings
      if (this.company.pointOfContact) {
        this.spocs = this.company.pointOfContact.split(',').map(s => s.trim());
      } else {
        this.spocs = [''];
      }
      
      if ((this.company as any).spocPhoneNumbers) {
        this.spocPhones = (this.company as any).spocPhoneNumbers.split(',').map((p: string) => p.trim());
      } else {
        this.spocPhones = [''];
      }
      
      if ((this.company as any).spocEmails) {
        this.spocEmails = (this.company as any).spocEmails.split(',').map((e: string) => e.trim());
      } else {
        this.spocEmails = [''];
      }
      
      if ((this.company as any).spocDesignations) {
        this.spocDesignations = (this.company as any).spocDesignations.split(',').map((d: string) => d.trim());
      } else {
        this.spocDesignations = [''];
      }
      
      // Ensure all arrays have same length
      const maxLength = Math.max(this.spocs.length, this.spocPhones.length, this.spocEmails.length, this.spocDesignations.length);
      while (this.spocs.length < maxLength) this.spocs.push('');
      while (this.spocPhones.length < maxLength) this.spocPhones.push('');
      while (this.spocEmails.length < maxLength) this.spocEmails.push('');
      while (this.spocDesignations.length < maxLength) this.spocDesignations.push('');
      
      this.editForm.patchValue({
        name: this.company.name,
        ceoName: this.company.ceoName,
        aboutCompany: this.company.aboutCompany,
        budget: this.company.budget,
        monthlyBudget: this.company.monthlyBudget,
        momGrowthPercent: this.company.momGrowthPercent,
        currency: this.company.currency || 'USD',
        isActive: this.company.isActive
      });
    }
  }

  addSpoc(): void {
    this.spocs.push('');
    this.spocPhones.push('');
    this.spocEmails.push('');
    this.spocDesignations.push('');
  }

  removeSpoc(index: number): void {
    if (this.spocs.length > 1) {
      this.spocs.splice(index, 1);
      this.spocPhones.splice(index, 1);
      this.spocEmails.splice(index, 1);
      this.spocDesignations.splice(index, 1);
    }
  }

  trackByIndex(index: number): number {
    return index;
  }

  toggleEditMode(): void {
    this.editMode = !this.editMode;
    if (this.editMode) {
      this.populateForm();
    }
    this.successMessage = null;
    this.errorMessage = null;
  }

  saveChanges(): void {
    if (this.editForm.invalid || !this.company || !this.company.id) {
      this.editForm.markAllAsTouched();
      this.errorMessage = 'Please fill in all required fields correctly.';
      return;
    }

    this.submitting = true;
    this.successMessage = null;
    this.errorMessage = null;

    // Join SPOCs and related data with comma
    const pointOfContact = this.spocs.filter(s => s.trim()).join(', ');
    const spocPhoneNumbers = this.spocPhones.filter(p => p.trim()).join(', ');
    const spocEmails = this.spocEmails.filter(e => e.trim()).join(', ');
    const spocDesignations = this.spocDesignations.filter(d => d.trim()).join(', ');
    const formData = { ...this.editForm.value, pointOfContact, spocPhoneNumbers, spocEmails, spocDesignations };

    this.svc.update(this.company.id, formData).subscribe({
      next: (updated) => {
        this.company = updated;
        this.successMessage = '✓ Company updated successfully!';
        this.submitting = false;
        this.editMode = false;
      },
      error: (err) => {
        this.errorMessage = 'Failed to update company. Please try again.';
        console.error('Error updating company:', err);
        this.submitting = false;
      }
    });
  }

  cancelEdit(): void {
    this.editMode = false;
    this.populateForm();
    this.successMessage = null;
    this.errorMessage = null;
  }

  canEdit(): boolean {
    const currentUser = this.authService.getCurrentUser();
    if (currentUser?.role === 'ADMIN') return true;
    if (currentUser?.role === 'COMPANY' && this.company) {
      const userCompanies = (currentUser as any)?.companies || [];
      return userCompanies.some((c: any) => c.id === this.company!.id);
    }
    return false;
  }

  getFieldError(fieldName: string): string {
    const field = this.editForm.get(fieldName);
    if (field?.hasError('required')) return 'This field is required';
    if (field?.hasError('minlength')) return `Minimum ${field.errors?.['minlength'].requiredLength} characters required`;
    if (field?.hasError('maxlength')) return `Maximum ${field.errors?.['maxlength'].requiredLength} characters allowed`;
    if (field?.hasError('min')) return 'Value must be positive';
    return '';
  }

  // Bank Details Methods
  populateBankForm(): void {
    if (this.company?.bankDetails) {
      this.bankForm.patchValue({
        bankName: this.company.bankDetails.bankName,
        accountNumber: this.company.bankDetails.accountNumber,
        ifsc: this.company.bankDetails.ifsc,
        accountHolderName: this.company.bankDetails.accountHolderName
      });
    }
  }

  toggleBankEditMode(): void {
    this.bankEditMode = !this.bankEditMode;
    if (this.bankEditMode) {
      this.populateBankForm();
    }
    this.bankSuccessMessage = null;
    this.bankErrorMessage = null;
  }

  saveBankDetails(): void {
    if (this.bankForm.invalid || !this.company || !this.company.id) {
      this.bankForm.markAllAsTouched();
      this.bankErrorMessage = 'Please fill in all required fields correctly.';
      return;
    }

    this.bankSubmitting = true;
    this.bankSuccessMessage = null;
    this.bankErrorMessage = null;

    // Update company with bank details
    const updatedCompany = {
      ...this.company,
      bankDetails: this.bankForm.value
    };

    this.svc.update(this.company.id, updatedCompany).subscribe({
      next: (updated) => {
        this.company = updated;
        this.bankSuccessMessage = '✓ Bank details updated successfully!';
        this.bankSubmitting = false;
        this.bankEditMode = false;
      },
      error: (err) => {
        this.bankErrorMessage = 'Failed to update bank details. Please try again.';
        console.error('Error updating bank details:', err);
        this.bankSubmitting = false;
      }
    });
  }

  cancelBankEdit(): void {
    this.bankEditMode = false;
    this.populateBankForm();
    this.bankSuccessMessage = null;
    this.bankErrorMessage = null;
  }

  getBankFieldError(fieldName: string): string {
    const field = this.bankForm.get(fieldName);
    if (field?.hasError('required')) return 'This field is required';
    return '';
  }
}
