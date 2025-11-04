package com.example.demo.Service;

import com.example.demo.Entity.Company;
import java.util.List;

public interface CompanyService {
    Company addCompany(Company company);

    Company updateCompany(Long id, Company company);

    void deleteCompany(Long id);

    List<Company> getAllCompanies();

    List<Company> getCompaniesByStatus(Boolean isActive);
}
