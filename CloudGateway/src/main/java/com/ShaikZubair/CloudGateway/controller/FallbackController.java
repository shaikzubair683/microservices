package com.ShaikZubair.CloudGateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @GetMapping("/orderServiceFallback")
    public String orderServiceFallback(){
        return "Order service is down!";
    }
    @GetMapping("/paymentServiceFallback")
    public String paymentServiceFallback(){
        return "payment service is down!";
    }

    @GetMapping("/productServiceFallback")
    public String productServiceFallback(){
        return "product service is down!";
    }
}
