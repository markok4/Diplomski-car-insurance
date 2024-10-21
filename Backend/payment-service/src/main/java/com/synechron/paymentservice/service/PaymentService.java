package com.synechron.paymentservice.service;

import com.synechron.paymentservice.entity.Payment;
import com.synechron.paymentservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    @Autowired
    PaymentRepository repository;

    public PaymentService (){}
    public List<Payment> getAll()
    {
        try {
            return repository.findAll();
        }
        catch (Exception e){
            throw e;
        }
    }
}
