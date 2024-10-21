package com.synechron.usermanagement.repository;

import com.synechron.usermanagement.model.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Page<Address> findAllByCityId(Long cityId, Pageable pageable);
}
