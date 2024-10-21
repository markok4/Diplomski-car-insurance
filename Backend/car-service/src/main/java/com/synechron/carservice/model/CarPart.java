package com.synechron.carservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CarPart {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @ManyToMany(mappedBy = "carParts")
    private Set<Car> cars;

    @Column(columnDefinition = "boolean default false")
    private Boolean isDeleted = false;
}
