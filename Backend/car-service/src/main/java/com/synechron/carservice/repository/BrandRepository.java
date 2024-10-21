package com.synechron.carservice.repository;

import com.synechron.carservice.model.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

public interface BrandRepository extends JpaRepository<Brand, Long>{
    Page<Brand> findByIsDeletedFalse(Pageable pageable);



}
