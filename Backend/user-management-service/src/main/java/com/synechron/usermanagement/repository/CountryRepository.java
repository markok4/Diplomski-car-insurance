package com.synechron.usermanagement.repository;

import com.synechron.usermanagement.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
