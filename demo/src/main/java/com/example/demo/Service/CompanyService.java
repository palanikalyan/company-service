package com.example.demo.Service;

import java.util.List;

import com.example.demo.Entity.Company;

public interface CompanyService {
    Company addCompany(Company company);
    Company updateCompany(Long id, Company company);
    void deleteCompany(Long id);
    List<Company> getAllCompanies();
    List<Company> getCompaniesByStatus(Boolean isActive);

    // 🔹 Add this method for AdminController
    Company toggleCompanyStatus(Long id, boolean status);
}
