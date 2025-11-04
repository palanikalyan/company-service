package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

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

    private Boolean isActive = true;

}