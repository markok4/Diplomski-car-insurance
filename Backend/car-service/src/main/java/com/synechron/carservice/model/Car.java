package com.synechron.carservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Year;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Car {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Year year;

    @Column
    private String image;

    @Column(columnDefinition = "boolean default false")
    private Boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "idModel")
    private Model model = new Model();

    @ManyToMany
    @JoinTable(
            name = "cars_parts",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "part_id")
    )
    Set<CarPart> carParts;

}
