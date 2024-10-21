package com.synechron.carservice.repository;

import com.synechron.carservice.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModelRepository extends JpaRepository<Model, Long> {
    List<Model> findAllByBrandId(Long brandId);

}