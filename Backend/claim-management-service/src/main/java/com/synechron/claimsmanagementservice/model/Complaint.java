package com.synechron.claimsmanagementservice.model;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String complaintDecision;
    @Column(nullable = false)
    private LocalDateTime dateOfComplaint;
    @Column
    private String description;
    @Column(nullable = false)
    private Boolean isDeleted = false;

    @OneToOne
    @JoinColumn(name = "claim_properties_id", nullable = false)
    private ClaimProperties claimProperties;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "complaint")
    private Set<Document> documents;

}
