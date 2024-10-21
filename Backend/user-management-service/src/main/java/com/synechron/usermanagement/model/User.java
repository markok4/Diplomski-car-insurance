package com.synechron.usermanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Collections;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
public class User extends Person {
    private String email;
    @JsonIgnore
    private String password;
    private Boolean isEnabled;
    private Boolean isActive;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;
    private UserRole userRole;
    @OneToMany(mappedBy = "user")
    private Set<Task> tasks = Collections.emptySet();
}
