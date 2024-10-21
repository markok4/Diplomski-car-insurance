package com.synechron.paymentservice.controller;

import com.synechron.paymentservice.service.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@Tag(name = "Payment")
@PreAuthorize("hasAuthority('ADMINISTRATOR')")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
}
