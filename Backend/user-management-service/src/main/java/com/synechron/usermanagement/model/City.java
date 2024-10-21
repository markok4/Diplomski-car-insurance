package com.synechron.usermanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Set;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Zip> zipCodes = Collections.emptySet();
    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Address> addresses = Collections.emptySet();
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
    private Boolean isDeleted = false;
}
