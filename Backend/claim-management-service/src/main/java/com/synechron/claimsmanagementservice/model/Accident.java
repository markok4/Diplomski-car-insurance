package com.synechron.claimsmanagementservice.model;

import jakarta.persistence.*;
import jdk.jfr.BooleanFlag;
import lombok.*;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Accident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime dateOfIncident;
    @Column(nullable = false)
    private Boolean wasResponsible;
    @Column
    private String description;
    @Column(nullable = false)
    private Boolean isDeleted = false;
}
