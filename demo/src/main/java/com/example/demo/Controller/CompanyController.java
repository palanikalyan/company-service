package com.example.demo.Controller;


import com.example.demo.Entity.Company;
import com.example.demo.Service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CompanyController {

    private final CompanyService service;

    @PostMapping
    public ResponseEntity<Company> addCompany(
            @RequestBody Company company,
            @RequestParam(required = false) Long userId) {
        return ResponseEntity.ok(service.addCompany(company, userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable Long id, @RequestBody Company company) {
        return ResponseEntity.ok(service.updateCompany(id, company));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        service.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies(
            @RequestParam(required = false) String role,
            @RequestParam(required = false) Long userId) {
        
        // If role is ADMIN, return all companies
        if ("ADMIN".equals(role)) {
            return ResponseEntity.ok(service.getAllCompanies());
        }
        
        // If role is COMPANY and userId is provided, return only their company
        if ("COMPANY".equals(role) && userId != null) {
            return ResponseEntity.ok(service.getCompaniesByUserId(userId));
        }
        
        // Default: return all companies (for backward compatibility)
        return ResponseEntity.ok(service.getAllCompanies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
        return service.getCompanyById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{isActive}")
    public ResponseEntity<List<Company>> getCompaniesByStatus(@PathVariable Boolean isActive) {
        return ResponseEntity.ok(service.getCompaniesByStatus(isActive));
    }

    // ✅ New endpoint - get all company details
    @GetMapping("/details")
    public ResponseEntity<List<Company>> getAllCompanyDetails() {
        return ResponseEntity.ok(service.getAllCompanies());
    }
}
