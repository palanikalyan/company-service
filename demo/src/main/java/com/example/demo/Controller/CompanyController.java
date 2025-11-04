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
public class CompanyController {

    private final CompanyService service;

    @PostMapping
    public ResponseEntity<Company> addCompany(@RequestBody Company company) {
        return ResponseEntity.ok(service.addCompany(company));
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
    public ResponseEntity<List<Company>> getAllCompanies() {
        return ResponseEntity.ok(service.getAllCompanies());
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
