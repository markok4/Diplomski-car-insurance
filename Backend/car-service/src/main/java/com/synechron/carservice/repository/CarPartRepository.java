package com.synechron.carservice.repository;

import com.synechron.carservice.model.CarPart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarPartRepository extends JpaRepository<CarPart, Long> {
}
