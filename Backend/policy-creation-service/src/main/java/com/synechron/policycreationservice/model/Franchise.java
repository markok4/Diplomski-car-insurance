package com.synechron.policycreationservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Franchise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer percentage;
    private Boolean isDeleted = false;
    @ManyToOne
    @JoinColumn(name = "proposal_id")
    private Proposal proposal;
    @ManyToOne
    @JoinColumn(name = "insurance_item_id")
    private InsuranceItem insuranceItem;
}
