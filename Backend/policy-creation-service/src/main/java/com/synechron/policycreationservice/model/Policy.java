package com.synechron.policycreationservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateSigned;
    private LocalDateTime expiringDate;
    private LocalDateTime moneyReceivedDate;
    private Double amount;
    private Boolean isDeleted = false;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "proposal_id")
    private Proposal proposal;

    @Column(name = "subscriber_id")
    private Long subscriberId;

}
