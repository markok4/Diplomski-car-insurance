package com.synechron.claimsmanagementservice.model;

import com.synechron.claimsmanagementservice.enums.ClaimPropertiesStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ClaimProperties {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private LocalDateTime complaintTimeLimit;
    @Column(nullable = false)
    private LocalDateTime dateField;
    @Column
    private LocalDateTime dateFinished;
    @Column(nullable = false)
    private Boolean decision = false;
    @Column
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClaimPropertiesStatus status;
    @Column(nullable = false)
    private Boolean isDeleted = false;

    @OneToOne
    @JoinColumn(name = "accident_id", nullable = false)
    private Accident accident;

    @OneToMany(fetch = FetchType.EAGER, mappedBy="claimProperties")
    private List<Appraisal> appraisals;

    @OneToMany(fetch = FetchType.EAGER, mappedBy="claimProperties")
    private List<Rejection> rejections;

    @OneToMany(fetch = FetchType.EAGER, mappedBy="claimProperties")
    private Set<Document> documents;

    @OneToOne(mappedBy="claimProperties")
    private Complaint complaint;

}
