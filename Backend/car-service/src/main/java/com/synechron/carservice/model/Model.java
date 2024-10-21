package com.synechron.carservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Model {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "boolean default false")
    private Boolean isDeleted = false;

    @Column
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "model",cascade=CascadeType.ALL)
    private List<Car> cars = new ArrayList<>();

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "idBrand")
    private Brand brand = new Brand();

}
