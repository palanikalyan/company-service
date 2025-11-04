package com.example.demo.Service;



import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.Entity.BankDetails;
import com.example.demo.Repository.BankDetailsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BankDetailsServiceImpl implements BankDetailsService {

    private final BankDetailsRepository repository;

    @Override
    public BankDetails addBankDetails(BankDetails bankDetails) {
        return repository.save(bankDetails);
    }

    @Override
    public List<BankDetails> getAllBankDetails() {
        return repository.findAll();
    }

    @Override
    public Optional<BankDetails> getBankDetailsById(Long id) {
        return repository.findById(id);
    }

    @Override
    public BankDetails updateBankDetails(Long id, BankDetails bankDetails) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setBankName(bankDetails.getBankName());
                    existing.setAccountNumber(bankDetails.getAccountNumber());
                    existing.setIfsc(bankDetails.getIfsc());
                    existing.setAccountHolderName(bankDetails.getAccountHolderName());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Bank details not found with id " + id));
    }

    @Override
    public void deleteBankDetails(Long id) {
        repository.deleteById(id);
    }
}
