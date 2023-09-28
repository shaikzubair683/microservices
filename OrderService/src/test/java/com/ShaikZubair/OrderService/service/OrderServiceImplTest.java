package com.ShaikZubair.OrderService.service;

import com.ShaikZubair.OrderService.entity.Order;
import com.ShaikZubair.OrderService.external.client.PaymentService;
import com.ShaikZubair.OrderService.external.client.ProductService;
import com.ShaikZubair.OrderService.repository.OrderRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;


@SpringBootTest
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductService productService;
    @Mock
    private PaymentService paymentService;
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    OrderService orderService = new OrderServiceImpl();

    Order order = new Order();



}