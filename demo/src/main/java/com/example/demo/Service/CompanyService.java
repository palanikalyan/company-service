package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import com.example.demo.Entity.Company;

public interface CompanyService {
    Company addCompany(Company company, Long userId);
    Company updateCompany(Long id, Company company);
    void deleteCompany(Long id);
    List<Company> getAllCompanies();
    List<Company> getCompaniesByStatus(Boolean isActive);
    List<Company> getCompaniesByUserId(Long userId);
    Optional<Company> getCompanyById(Long id);

    // 🔹 Add this method for AdminController
    Company toggleCompanyStatus(Long id, boolean status);
}
