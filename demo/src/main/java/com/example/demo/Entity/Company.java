package com.example.demo.Entity;

import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "ceo_name", length = 100)
    private String ceoName;

    @Column(name = "point_of_contact", length = 100)
    private String pointOfContact;

    @Column(name = "about_company", length = 1000)
    private String aboutCompany;

    @Builder.Default
    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(precision = 19, scale = 2)
    private BigDecimal budget;

    @Column(name = "monthly_budget", precision = 19, scale = 2)
    private BigDecimal monthlyBudget;

    @Column(name = "mom_growth_percent", precision = 5, scale = 2)
    private BigDecimal momGrowthPercent;

    @Column(length = 10)
    private String currency = "USD";

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "bank_details_id")
    private BankDetails bankDetails;

    @Column(name = "pan_number", length = 20)
    private String panNumber;
}
