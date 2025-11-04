package com.example.demo.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Entity.Company;
import com.example.demo.Repository.CompanyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository repository;

    @Override
    public Company addCompany(Company company) {
        // Ensure the ID is always auto-generated
        company.setId(null);
        return repository.save(company);
    }

    @Override
    public Company updateCompany(Long id, Company updated) {
        Company company = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + id));

        // Basic details
        company.setName(updated.getName());
        company.setCeoName(updated.getCeoName());
        company.setPointOfContact(updated.getPointOfContact());
        company.setAboutCompany(updated.getAboutCompany());
        company.setIsActive(updated.getIsActive());
        company.setPanNumber(updated.getPanNumber());

        // Financial details
        company.setBudget(updated.getBudget());
        company.setMonthlyBudget(updated.getMonthlyBudget());
        company.setMomGrowthPercent(updated.getMomGrowthPercent());
        company.setCurrency(updated.getCurrency());

        // Bank details: deep merge or replace
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
            // If cleared by client
            company.setBankDetails(null);
        }

        return repository.save(company);
    }

    @Override
    public void deleteCompany(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Company not found with id: " + id);
        }
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

    @Override
    public Company toggleCompanyStatus(Long id, boolean status) {
        return repository.findById(id)
                .map(company -> {
                    company.setIsActive(status);
                    return repository.save(company);
                })
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + id));
    }
}
