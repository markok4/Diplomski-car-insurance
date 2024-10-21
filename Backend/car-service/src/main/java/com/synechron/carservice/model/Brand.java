package com.synechron.carservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Brand {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private LocalDate creationDate;


    @Column(nullable = false)
    private String logoImage;

    @Column(columnDefinition = "boolean default false")
    private Boolean isDeleted = false;

    @JsonIgnore
    @OneToMany(mappedBy = "brand")
    private Set<Model> models = new HashSet<>();

}
