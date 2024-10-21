package com.synechron.usermanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Set;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    private String streetNumber;
    @ManyToOne
    @JoinColumn(name = "zip_id")
    private Zip zip;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
    @OneToMany(mappedBy = "address")
    private Set<User> users = Collections.emptySet();
    private Boolean isDeleted = false;
}
