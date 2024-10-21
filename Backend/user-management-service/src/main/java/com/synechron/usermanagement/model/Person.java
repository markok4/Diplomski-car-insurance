package com.synechron.usermanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String jmbg;
    private LocalDateTime birth;
    private Gender gender;
    private MaritialStatus maritialStatus;
    @OneToOne
    @JoinColumn(name = "contact_id")
    private Contact contact;
    private Boolean isDeleted = false;
    private String profileImage;
}
