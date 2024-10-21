package com.synechron.paymentservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cheque_payment")
@DiscriminatorValue("cheque")
public class ChequePayment extends Payment {

    @Column(name = "cheque_no")
    private Long chequeNo;
    @Column(name = "issuer_name")
    private String issuerName;
    @Column(name = "owner_name")
    private String ownerName;
    @Column(name = "cheque_date")
    private LocalDateTime chequeDate;
    @Column(name = "is_deleted")
    private Boolean isDeleted = false;
}

