package com.synechron.carservice.repository;

import com.synechron.carservice.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Year;
import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    Page<Car> findByIsDeletedFalse(Pageable pageable);

    List<Car> findAllByModelId(Long modelId);

    List<Car> findAllByModelBrandId(Long brandId);

    List<Car> findAllByYear(Year year);

    List<Car> findAllByModelIdAndYear(Long modelId, Year year);

    List<Car> findAllByModelBrandIdAndYear(Long modelId, Year year);
}
