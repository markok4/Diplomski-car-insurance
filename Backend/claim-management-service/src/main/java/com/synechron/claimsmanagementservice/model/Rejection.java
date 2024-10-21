package com.synechron.claimsmanagementservice.model;

import com.synechron.claimsmanagementservice.enums.RejectionReason;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Rejection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime dateOfRejection;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RejectionReason rejectionReason;
    @Column
    private String description;
    @Column(nullable = false)
    private Boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "claim_properties_id", nullable = false)
    private ClaimProperties claimProperties;
}
