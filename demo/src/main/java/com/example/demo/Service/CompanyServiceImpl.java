package com.example.demo.Service;

import com.example.demo.Entity.Company;
import com.example.demo.Repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository repository;

    @Override
    public Company addCompany(Company company) {
        // Ensure client-supplied id (if any) is ignored so the DB generates it
        company.setId(null);
        return repository.save(company);
    }

    @Override
    public Company updateCompany(Long id, Company updated) {
        Company company = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        company.setName(updated.getName());
        company.setCeoName(updated.getCeoName());
        company.setPointOfContact(updated.getPointOfContact());
        company.setAboutCompany(updated.getAboutCompany());
        company.setIsActive(updated.getIsActive());
    // Financial fields
    company.setBudget(updated.getBudget());   
        // Bank details: merge or set as needed
        if (updated.getBankDetails() != null) {
            if (company.getBankDetails() == null) {
                company.setBankDetails(updated.getBankDetails());
            } else {
                var existing = company.getBankDetails();
                var incoming = updated.getBankDetails();
                existing.setBankName(incoming.getBankName());
                existing.setAccountNumber(incoming.getAccountNumber());
                existing.setIfsc(incoming.getIfsc());
                existing.setAccountHolderName(incoming.getAccountHolderName());
            }
        } else {
            // If client cleared bankDetails, remove it
            company.setBankDetails(null);
        }
        return repository.save(company);
    }

    @Override
    public void deleteCompany(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Company> getAllCompanies() {
        return repository.findAll();
    }

    @Override
    public List<Company> getCompaniesByStatus(Boolean isActive) {
        return repository.findByIsActive(isActive);
    }
}
