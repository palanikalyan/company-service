package com.example.demo.Service;

import com.example.demo.Entity.Company;
import com.example.demo.Repository.CompanyRepository;
import com.example.demo.Service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository repository;

    @Override
    public Company addCompany(Company company) {
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
