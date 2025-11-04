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





    // Bank details moved to BankDetails entity
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "bank_details_id")
    private BankDetails bankDetails;




}