package com.synechron.paymentservice.entity;

import com.synechron.paymentservice.enumeration.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "card_payment")
@DiscriminatorValue("card")
public class CardPayment extends Payment{
    @Column(name = "card_holder")
    private String cardHolder;
    @Column(name = "card_number")
    private Long cardNumber;
    @Column(name = "expired_date")
    private Date expiredDate;
    @Column(name = "is_deleted")
    private Boolean isDeleted=false;
    @ManyToOne
    @JoinColumn(name = "card_type_id")
    private CardType cardType;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

}
