package com.example.demo.Config;

import com.example.demo.Entity.BankDetails;
import com.example.demo.Entity.Company;
import com.example.demo.Entity.User;
import com.example.demo.Repository.CompanyRepository;
import com.example.demo.Repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    @PostConstruct
    public void init() {
        // Insert Bank Details - these will be cascaded when saving companies
        BankDetails bank1 = BankDetails.builder()
                .bankName("First National Bank")
                .accountNumber("1234567890")
                .ifsc("SBIN0001234")
                .accountHolderName("TechNova Ltd")
                .build();
        
        BankDetails bank2 = BankDetails.builder()
                .bankName("State Bank")
                .accountNumber("0987654321")
                .ifsc("SBIN0005678")
                .accountHolderName("NextGen Systems")
                .build();
        
        BankDetails bank3 = BankDetails.builder()
                .bankName("CityBank")
                .accountNumber("555666777888")
                .ifsc("ICIC0001111")
                .accountHolderName("CodeCrafters")
                .build();
        
        BankDetails bank4 = BankDetails.builder()
                .bankName("HDFC Bank")
                .accountNumber("111222333444")
                .ifsc("HDFC0009876")
                .accountHolderName("FutureVision Pvt Ltd")
                .build();
        
        BankDetails bank5 = BankDetails.builder()
                .bankName("Axis Bank")
                .accountNumber("777888999000")
                .ifsc("UTIB0004321")
                .accountHolderName("Innovatech Solutions")
                .build();

        // Insert Companies (bank details will be saved via cascade)
        Company company1 = Company.builder()
                .name("TechNova Ltd")
                .ceoName("Ravi Kumar")
                .pointOfContact("Meena Patel")
                .aboutCompany("We build enterprise SaaS products and provide scalable cloud solutions.")
                .isActive(true)
                .budget(new BigDecimal("1000000.00"))
                .monthlyBudget(new BigDecimal("83333.33"))
                .momGrowthPercent(new BigDecimal("2.50"))
                .currency("USD")
                .bankDetails(bank1)
                .panNumber("AACCT1234N")
                .build();

        Company company2 = Company.builder()
                .name("NextGen Systems")
                .ceoName("Aditi Sharma")
                .pointOfContact("Rahul Jain")
                .aboutCompany("IT consulting and digital transformation solutions.")
                .isActive(false)
                .budget(new BigDecimal("500000.00"))
                .monthlyBudget(new BigDecimal("41666.67"))
                .momGrowthPercent(new BigDecimal("-1.20"))
                .currency("USD")
                .bankDetails(bank2)
                .panNumber("BBXCS5678M")
                .build();

        Company company3 = Company.builder()
                .name("CodeCrafters")
                .ceoName("Arjun Rao")
                .pointOfContact("Sneha Iyer")
                .aboutCompany("Freelance software and creative design agency.")
                .isActive(true)
                .budget(new BigDecimal("150000.00"))
                .monthlyBudget(new BigDecimal("12500.00"))
                .momGrowthPercent(new BigDecimal("5.00"))
                .currency("USD")
                .bankDetails(bank3)
                .panNumber("CCVDE9876L")
                .build();

        Company company4 = Company.builder()
                .name("FutureVision Pvt Ltd")
                .ceoName("Priya Mehta")
                .pointOfContact("Kunal Joshi")
                .aboutCompany("AI-driven analytics and business automation solutions.")
                .isActive(true)
                .budget(new BigDecimal("2500000.00"))
                .monthlyBudget(new BigDecimal("208333.33"))
                .momGrowthPercent(new BigDecimal("4.75"))
                .currency("INR")
                .bankDetails(bank4)
                .panNumber("DDFGT5432R")
                .build();

        Company company5 = Company.builder()
                .name("Innovatech Solutions")
                .ceoName("Vikram Das")
                .pointOfContact("Sonia Gupta")
                .aboutCompany("Cutting-edge R&D for IoT and smart devices.")
                .isActive(true)
                .budget(new BigDecimal("750000.00"))
                .monthlyBudget(new BigDecimal("62500.00"))
                .momGrowthPercent(new BigDecimal("3.20"))
                .currency("USD")
                .bankDetails(bank5)
                .panNumber("EEFHG2233P")
                .build();

        companyRepository.save(company1);
        companyRepository.save(company2);
        companyRepository.save(company3);
        companyRepository.save(company4);
        companyRepository.save(company5);

        // Insert Users (after companies are created)
        // Neha has access to company1 and company3
        User user1 = User.builder()
                .username("neha")
                .password("neha123")
                .email("neha@gmail.com")
                .role("COMPANY")
                .build();
        user1.getCompanies().add(company1);
        user1.getCompanies().add(company3);

        // Kiran has access to company2 and company4
        User user2 = User.builder()
                .username("kiran")
                .password("kiran123")
                .email("kiran@gmail.com")
                .role("COMPANY")
                .build();
        user2.getCompanies().add(company2);
        user2.getCompanies().add(company4);

        // Admin has no companies
        User admin = User.builder()
                .username("admin")
                .password("admin123")
                .email("admin@company.com")
                .role("ADMIN")
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(admin);

        System.out.println("✅ Data initialized successfully!");
    }
}
