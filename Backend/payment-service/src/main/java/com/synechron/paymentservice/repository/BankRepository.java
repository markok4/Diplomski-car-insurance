package com.synechron.paymentservice.repository;

import com.synechron.paymentservice.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank,Long> {
}
