package com.synechron.paymentservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "card_type")
public class CardType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "card_holder")
    private String cardHolder;

    @Column(name = "card_number")
    private Long cardNumber;

    @Column(name = "card_type")
    private String cardType;

    @Column(name = "is_deleted")
    private Boolean isDeleted=false;

    @OneToMany(mappedBy = "cardType")
    private List<CardPayment> cardPaymentList = new ArrayList<>();
}
