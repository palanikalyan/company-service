package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String ceoName;
    private String pointOfContact;
    @Column(length = 1000)
    private String aboutCompany;

    @Builder.Default
    private Boolean isActive = true;

    @Column(precision = 19, scale = 2)
    private BigDecimal budget;

    @Column(name = "monthly_budget", precision = 19, scale = 2)
    private BigDecimal monthlyBudget;

    @Column(name = "mom_growth_percent")
    private Double momGrowthPercent;

    @Column(length = 10)
    private String currency;

    // Bank details
    @Column(name = "bank_name", length = 200)
    private String bankName;

    @Column(name = "account_number", length = 100)
    private String accountNumber;

    @Column(length = 34)
    private String ifsc;

    @Column(name = "account_holder", length = 200)
    private String accountHolderName;




}