package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bank_name", length = 200)
    private String bankName;

    @Column(name = "account_number", length = 100)
    private String accountNumber;

    @Column(length = 34)
    private String ifsc;

    @Column(name = "account_holder", length = 200)
    private String accountHolderName;

}
