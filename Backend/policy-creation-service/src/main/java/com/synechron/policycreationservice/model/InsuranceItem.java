package com.synechron.policycreationservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class InsuranceItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Boolean isOptional;
    private Integer franchisePercentage;
    private Double amount;
    private Boolean isDeleted = false;
    @ManyToMany(mappedBy = "insuranceItems")
    private List<InsurancePlan> insurancePlans = new ArrayList<InsurancePlan>();
    @OneToMany(mappedBy = "insuranceItem")
    private List<Franchise> franchises = new ArrayList<Franchise>();
}
