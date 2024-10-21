package com.synechron.paymentservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bank_payment")
@DiscriminatorValue("bank")
public class BankPayment extends Payment {
    @Column(name = "bank_name")
    private String bankName;
    @Column(name = "transaction_no")
    private Long transactionNo;
    @Column(name = "is_blocked")
    private Boolean isBlocked;
    @Column(name = "is_deleted")
    private Boolean isDeleted = false;
    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;
}

