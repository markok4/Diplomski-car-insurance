package com.synechron.usermanagement.repository;

import com.synechron.usermanagement.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    Boolean existsByEmail(String email);
}
