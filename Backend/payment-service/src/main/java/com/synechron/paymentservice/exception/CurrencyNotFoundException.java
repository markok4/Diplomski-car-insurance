package com.synechron.paymentservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CurrencyNotFoundException extends RuntimeException{
    public CurrencyNotFoundException(Long id) {
        super("Currency not found with id: " + id);
    }
}
