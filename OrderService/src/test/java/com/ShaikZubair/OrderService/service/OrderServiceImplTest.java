package com.ShaikZubair.OrderService.service;

import com.ShaikZubair.OrderService.external.client.ProductService;
import com.ShaikZubair.OrderService.repository.OrderRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductService productService;

    @InjectMocks
    OrderServiceImpl orderService = new OrderServiceImpl();

}