package com.synechron.paymentservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "currency")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Currency name is required")
    @Column(name = "name")
    private String name;

    @Column(name = "code", columnDefinition = "VARCHAR(3) default 'USD'")
    private String code;

    @Column(name = "logo")
    private String logo;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Date creationDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_updated")
    private Date lastUpdated;

    @Column(name = "is_deleted")
    private Boolean isDeleted=false; // Indicates whether the currency is active

    @Column(name = "is_valid")
    private Boolean isValid=true;

    @OneToMany(mappedBy = "currency")
    List<Payment> payments = new ArrayList<>();
}
