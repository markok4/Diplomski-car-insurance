package com.synechron.claimsmanagementservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Appraisal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime dateOfAppraisalStart;
    @Column
    private LocalDateTime dateOfAppraisalFinish;
    @Column
    private String description;
    @Column
    private Double estimatedPrice;
    @Column(nullable = false)
    private Boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "claim_properties_id", nullable = false)
    private ClaimProperties claimProperties;
}
