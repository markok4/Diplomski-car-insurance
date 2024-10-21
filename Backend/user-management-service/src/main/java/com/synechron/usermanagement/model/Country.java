package com.synechron.usermanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String abbreviation;
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<City> cities = Collections.emptySet();
    private Boolean isDeleted = false;
}
