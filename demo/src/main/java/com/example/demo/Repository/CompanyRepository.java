package com.example.demo.Repository;


import com.example.demo.Entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    List<Company> findByIsActive(Boolean isActive);
}
