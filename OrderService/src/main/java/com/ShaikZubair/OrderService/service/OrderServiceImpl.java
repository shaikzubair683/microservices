package com.ShaikZubair.OrderService.service;


import com.ShaikZubair.OrderService.entity.Order;
import com.ShaikZubair.OrderService.exception.CustomException;
import com.ShaikZubair.OrderService.external.client.PaymentService;
import com.ShaikZubair.OrderService.external.client.ProductService;
import com.ShaikZubair.OrderService.external.request.PaymentRequest;
import com.ShaikZubair.OrderService.external.response.PaymentResponse;
import com.ShaikZubair.OrderService.external.response.ProductResponse;
import com.ShaikZubair.OrderService.model.OrderRequest;
import com.ShaikZubair.OrderService.model.OrderResponse;
import com.ShaikZubair.OrderService.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.ArrayList;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public long placeOrder(OrderRequest orderRequest) {
        log.info("placing order request: {}", orderRequest);
        productService.reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());
        Order order = Order.builder()
                .productId(orderRequest.getProductId())
                .quantity(orderRequest.getQuantity())
                .orderDate(Instant.now())
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .build();

        orderRepository.save(order);
        log.info("order created with id {}", order.getId());

        log.info("Calling Payment Service to complete the payment");
        PaymentRequest paymentRequest
                = PaymentRequest.builder()
                .orderId(order.getId())
                .paymentMethod(orderRequest.getPaymentMethod())
                .amount(orderRequest.getTotalAmount())
                .build();
        String orderStatus = null;

        try {
            paymentService.doPayment(paymentRequest);
            log.info("Payment done Successfully. Changing the Oder status to PLACED");
            orderStatus = "PLACED";
        } catch (Exception e) {
            log.error("Error occurred in payment. Changing order status to PAYMENT_FAILED");
            orderStatus = "PAYMENT_FAILED";
        }

        order.setOrderStatus(orderStatus);
        orderRepository.save(order);

        log.info("Order Places successfully with Order Id: {}", order.getId());
        return order.getId();

    }
    



}
