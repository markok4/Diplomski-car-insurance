package com.synechron.policycreationservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
public class InsurancePlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Boolean isPremium;
    private Boolean isDeleted = false;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "InsuranceItemInsurancePlan",
            joinColumns = @JoinColumn(name = "insurance_plan_id"),
            inverseJoinColumns = @JoinColumn(name = "insurance_item_id")
    )
    private List<InsuranceItem> insuranceItems = new ArrayList<>();
    @OneToMany(mappedBy = "insurancePlan", fetch = FetchType.EAGER)
    private List<Proposal> proposals = new ArrayList<>();
}
