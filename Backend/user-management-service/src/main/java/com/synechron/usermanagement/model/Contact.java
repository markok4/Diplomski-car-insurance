package com.synechron.usermanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String homePhone;
    private String mobilePhone;
    private String email;
    @OneToOne(mappedBy = "contact")
    private Person person;
    private Boolean isDeleted = false;
}
