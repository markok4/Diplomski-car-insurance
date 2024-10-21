package com.synechron.policycreationservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Proposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean isValid;
    private LocalDateTime creationDate;
    @Enumerated(EnumType.STRING)
    private ProposalStatus proposalStatus;
    private Double amount;
    private String carPlates;
    private Boolean isDeleted = false;
    @ManyToOne
    @JoinColumn(name = "insurance_plan_id")
    private InsurancePlan insurancePlan;
    @OneToOne(mappedBy = "proposal")
    private Policy policy;
    @OneToMany(mappedBy = "proposal", fetch = FetchType.EAGER)
    private List<Franchise> franchises = new ArrayList<>();
    private String salesAgentEmail;
    @Column(name = "subscriber_id")
    private Long subscriberId;
    private Long carId;
}