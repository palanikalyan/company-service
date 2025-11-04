package com.example.demo.Controller;



import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.BankDetails;
import com.example.demo.Service.BankDetailsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/banks")
@RequiredArgsConstructor
public class BankDetailsController {

    private final BankDetailsService service;

    @PostMapping
    public ResponseEntity<BankDetails> addBank(@RequestBody BankDetails bankDetails) {
        return ResponseEntity.ok(service.addBankDetails(bankDetails));
    }

    @GetMapping
    public ResponseEntity<List<BankDetails>> getAllBanks() {
        return ResponseEntity.ok(service.getAllBankDetails());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankDetails> getBankById(@PathVariable Long id) {
        return service.getBankDetailsById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankDetails> updateBank(@PathVariable Long id, @RequestBody BankDetails bankDetails) {
        return ResponseEntity.ok(service.updateBankDetails(id, bankDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBank(@PathVariable Long id) {
        service.deleteBankDetails(id);
        return ResponseEntity.noContent().build();
    }
}
