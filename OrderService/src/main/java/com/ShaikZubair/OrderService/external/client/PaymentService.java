package com.ShaikZubair.OrderService.external.client;

import com.ShaikZubair.OrderService.exception.CustomException;
import com.ShaikZubair.OrderService.external.request.PaymentRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface PaymentService {

    @PostMapping
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest);

    default ResponseEntity<Long> fallback(Exception e) {
        throw new CustomException("Payment Service is not available",
                "UNAVAILABLE",
                500);
    }
}

