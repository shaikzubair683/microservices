package com.ShaikZubair.PaymentService.controller;


import com.ShaikZubair.PaymentService.model.PaymentRequest;
import com.ShaikZubair.PaymentService.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;


   @PostMapping
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest request){
        return new ResponseEntity<>(paymentService.doPayment(request) ,
                HttpStatus.OK);
    }

}
