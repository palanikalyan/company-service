import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, Validators, FormGroup } from '@angular/forms';
import { CompanyService } from '../../services/company.service';
import { AuthService } from '../../services/auth.service';
import { Router, RouterLink } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { CardModule } from 'primeng/card';
import { CheckboxModule } from 'primeng/checkbox';
import { TooltipModule } from 'primeng/tooltip';

@Component({
  selector: 'app-company-form',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, RouterLink, ButtonModule, InputTextModule, CardModule, CheckboxModule, TooltipModule],
  templateUrl: './company-form.component.html',
  styleUrls: ['./company-form.component.css']
})
export class CompanyFormComponent {
  form!: FormGroup;
  submitting = false;
  successMessage: string | null = null;
  errorMessage: string | null = null;
  spocs: string[] = [''];  // Array to hold multiple SPOCs
  spocPhones: string[] = [''];  // Array to hold SPOC phone numbers
  spocEmails: string[] = [''];  // Array to hold SPOC emails
  spocDesignations: string[] = [''];  // Array to hold SPOC designations

  constructor(
    private fb: FormBuilder, 
    private svc: CompanyService, 
    private authService: AuthService,
    private router: Router
  ) {
    this.form = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2)]],
      ceoName: ['', [Validators.minLength(2)]],
      aboutCompany: ['', [Validators.maxLength(500)]],
      budget: [null, [Validators.min(0)]],
      monthlyBudget: [null, [Validators.min(0)]],
      momGrowthPercent: [null],
      currency: ['USD'],
      isActive: [true]
    });
  }

  addSpoc() {
    this.spocs.push('');
    this.spocPhones.push('');
    this.spocEmails.push('');
    this.spocDesignations.push('');
  }

  removeSpoc(index: number) {
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

  submit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      this.errorMessage = 'Please fill in all required fields correctly.';
      return;
    }
    
    this.submitting = true;
    this.successMessage = null;
    this.errorMessage = null;
    
    const currentUser = this.authService.getCurrentUser();
    const userId = currentUser?.id;
    
    // Join SPOCs and related data with comma
    const pointOfContact = this.spocs.filter(s => s.trim()).join(', ');
    const spocPhoneNumbers = this.spocPhones.filter(p => p.trim()).join(', ');
    const spocEmails = this.spocEmails.filter(e => e.trim()).join(', ');
    const spocDesignations = this.spocDesignations.filter(d => d.trim()).join(', ');
    const formData = { ...this.form.value, pointOfContact, spocPhoneNumbers, spocEmails, spocDesignations };
    
    this.svc.create(formData, userId).subscribe(
      () => {
        this.successMessage = '✓ Company created successfully!';
        this.submitting = false;
        
        // Refresh user data to update companies list
        if (userId) {
          this.authService.refreshCurrentUser(userId).subscribe(() => {
            setTimeout(() => {
              this.router.navigate(['/company/dashboard']);
            }, 1500);
          });
        } else {
          setTimeout(() => {
            this.router.navigate(['/company/dashboard']);
          }, 1500);
        }
      },
      (error) => {
        this.errorMessage = 'Failed to create company. Please try again.';
        console.error('Error creating company:', error);
        this.submitting = false;
      }
    );
  }

  getFieldError(fieldName: string): string {
    const field = this.form.get(fieldName);
    if (field?.hasError('required')) return 'This field is required';
    if (field?.hasError('minlength')) return `Minimum ${field.errors?.['minlength'].requiredLength} characters required`;
    if (field?.hasError('maxlength')) return `Maximum ${field.errors?.['maxlength'].requiredLength} characters allowed`;
    if (field?.hasError('min')) return 'Value must be positive';
    return '';
  }
}

