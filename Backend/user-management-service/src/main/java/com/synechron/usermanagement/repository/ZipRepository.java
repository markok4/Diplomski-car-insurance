package com.synechron.usermanagement.repository;

import com.synechron.usermanagement.model.Address;
import com.synechron.usermanagement.model.Zip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZipRepository extends JpaRepository<Zip, Long> {
    Page<Zip> findAllByCityId(Long cityid, Pageable pageable);
}
