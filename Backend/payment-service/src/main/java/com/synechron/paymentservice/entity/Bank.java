package com.synechron.paymentservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bank")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "bank_name")
    private String bankName;
    @Column(name = "logo", length = 500)
    private String logo;
    @Column(name = "bank_address")
    private String bankAdress;
    @Column(name = "country")
    private String country;
    @Column(name = "city")
    private String city;
    @Column(name = "employee_number")
    private Integer employeeNumber;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "is_bankrupt")
    private Boolean isBankrupt=false;
    @Column(name = "is_deleted")
    private Boolean isDeleted=false;
    @OneToMany(mappedBy = "bank")
    private List<BankPayment> bankPayments = new ArrayList<>();
}
