package com.synechron.claimsmanagementservice.model;

import com.synechron.claimsmanagementservice.enums.FileType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DocumentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FileType fileType;
    @Column(nullable = false)
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "documentType")
    private Set<Document> documents;
}
