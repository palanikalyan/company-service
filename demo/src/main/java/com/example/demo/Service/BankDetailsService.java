package com.example.demo.Service;



import com.example.demo.Entity.BankDetails;
import java.util.List;
import java.util.Optional;

public interface BankDetailsService {
    BankDetails addBankDetails(BankDetails bankDetails);
    List<BankDetails> getAllBankDetails();
    Optional<BankDetails> getBankDetailsById(Long id);
    BankDetails updateBankDetails(Long id, BankDetails bankDetails);
    void deleteBankDetails(Long id);
}
