package com.synechron.claimsmanagementservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column
    private String originalName;
    @Column(nullable = false)
    private String path;
    @Column(nullable = false)
    private Boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "claim_properties_id")
    private ClaimProperties claimProperties;

    @ManyToOne
    @JoinColumn(name = "complaint_id")
    private Complaint complaint;

    @ManyToOne
    @JoinColumn(name = "document_type_id", nullable = false)
    private DocumentType documentType;

    @PrePersist
    @PreUpdate
    private void prepareData(){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("ddMMyyyy"); // for a date like "20230403" for April 3, 2023
        String currentDate = LocalDate.now().format(dateFormatter);
        // custom naming convention: prepend "DOC_" to all document names, this is applied when the document is first created or updated
        this.name = "DOC_" + originalName.toUpperCase() + "_" + currentDate;
    }

}
