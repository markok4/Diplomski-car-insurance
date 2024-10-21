package com.synechron.paymentservice.repository;

import com.synechron.paymentservice.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency,Long> {


}