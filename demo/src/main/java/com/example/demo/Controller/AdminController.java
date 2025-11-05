package com.example.demo.Controller;


import com.example.demo.Entity.BankDetails;
import com.example.demo.Entity.Company;
import com.example.demo.Entity.User;
import com.example.demo.Service.BankDetailsService;
import com.example.demo.Service.CompanyService;
import com.example.demo.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

    private final UserService userService;
    private final CompanyService companyService;
    private final BankDetailsService bankService;

    // ===================== AUTHENTICATION =====================
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.findByUsername(loginRequest.getUsername());
        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            List<LoginResponse.CompanyInfo> companyInfoList = user.getCompanies().stream()
                .map(company -> new LoginResponse.CompanyInfo(company.getId(), company.getName()))
                .toList();
            
            LoginResponse response = new LoginResponse(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                user.getEmail(),
                companyInfoList
            );
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }

    // ===================== USER MANAGEMENT =====================

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<LoginResponse> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user != null) {
            List<LoginResponse.CompanyInfo> companyInfoList = user.getCompanies().stream()
                .map(company -> new LoginResponse.CompanyInfo(company.getId(), company.getName()))
                .toList();
            
            LoginResponse response = new LoginResponse(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                user.getEmail(),
                companyInfoList
            );
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // ===================== COMPANY MANAGEMENT =====================

    @GetMapping("/companies")
    public ResponseEntity<List<Company>> getAllCompanies() {
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @GetMapping("/companies/status/{isActive}")
    public ResponseEntity<List<Company>> getCompaniesByStatus(@PathVariable Boolean isActive) {
        return ResponseEntity.ok(companyService.getCompaniesByStatus(isActive));
    }

    @DeleteMapping("/companies/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/companies/{id}/activate")
    public ResponseEntity<Company> activateCompany(@PathVariable Long id) {
        Company updated = companyService.toggleCompanyStatus(id, true);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/companies/{id}/deactivate")
    public ResponseEntity<Company> deactivateCompany(@PathVariable Long id) {
        Company updated = companyService.toggleCompanyStatus(id, false);
        return ResponseEntity.ok(updated);
    }

    // ===================== BANK MANAGEMENT =====================

    @GetMapping("/banks")
    public ResponseEntity<List<BankDetails>> getAllBanks() {
        return ResponseEntity.ok(bankService.getAllBankDetails());
    }

    @DeleteMapping("/banks/{id}")
    public ResponseEntity<Void> deleteBank(@PathVariable Long id) {
        bankService.deleteBankDetails(id);
        return ResponseEntity.noContent().build();
    }

}
