package com.synechron.paymentservice.repository;

import com.synechron.paymentservice.entity.PaymentMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentModeRepository extends JpaRepository<PaymentMode,Long> {

}
