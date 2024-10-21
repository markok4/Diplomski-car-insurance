package com.synechron.paymentservice.entity;


import jakarta.persistence.*;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="payment")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="payment_mode",
        discriminatorType = DiscriminatorType.STRING)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "is_deleted")
    private Boolean isDeleted=false;
    @ManyToOne
    @JoinColumn(name = "currency_mode_id")
    private Currency currency;

}
