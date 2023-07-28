package com.ShaikZubair.OrderService.service;


import com.ShaikZubair.OrderService.entity.Order;
import com.ShaikZubair.OrderService.external.client.ProductService;
import com.ShaikZubair.OrderService.model.OrderRequest;
import com.ShaikZubair.OrderService.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductService productService;
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


        return order.getId();

    }
}
