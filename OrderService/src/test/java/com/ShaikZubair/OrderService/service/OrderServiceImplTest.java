package com.ShaikZubair.OrderService.service;

import com.ShaikZubair.OrderService.entity.Order;
import com.ShaikZubair.OrderService.exception.CustomException;
import com.ShaikZubair.OrderService.external.client.PaymentService;
import com.ShaikZubair.OrderService.external.client.ProductService;
import com.ShaikZubair.OrderService.external.request.PaymentRequest;
import com.ShaikZubair.OrderService.external.response.ProductResponse;
import com.ShaikZubair.OrderService.model.GetOrderDetailsResponse;
import com.ShaikZubair.OrderService.model.OrderRequest;
import com.ShaikZubair.OrderService.model.PaymentMethod;
import com.ShaikZubair.OrderService.repository.OrderRepository;
import com.thoughtworks.xstream.mapper.Mapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.Instant;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;


@SpringBootTest
public class OrderServiceImplTest {


    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductService productService;
    @Mock
    private PaymentService paymentService;

    @InjectMocks
    OrderService orderService = new OrderServiceImpl();

    @Test
    @DisplayName("getOrderDetails - success scenario")
    void testWhenGetOrderDetailsSuccess(){
        //mock internal calls
        Order order = getMockOrder();
        Mockito.when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        ProductResponse productResponse = getMockProductResponse();
        Mockito.when(productService.getProductById(anyLong())).thenReturn(ResponseEntity.of(Optional.of(productResponse)));

        //make actual call
        ResponseEntity<GetOrderDetailsResponse> orderResponse= orderService.getOrderDetails(1);


        //Assert
        Assertions.assertNotNull(orderResponse);
        Assertions.assertEquals(order.getQuantity(),orderResponse.getBody().getOrderQuantity());

        //verify
        Mockito.verify(orderRepository, Mockito.times(1)).findById(anyLong());
        Mockito.verify(productService, Mockito.times(1)).getProductById(anyLong());

    }
    @Test
    @DisplayName("getOrderDetails - failure scenario")
    void testWhenGetOrderDetailsFailure(){
        //Mock
        Mockito.when(orderRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        //assert
        CustomException exception= Assertions.assertThrows(CustomException.class, ()-> orderService.getOrderDetails(1));
        Assertions.assertEquals("NOT_FOUND",exception.getErrorCode());
        Assertions.assertEquals(404,exception.getStatus());

        //verify
        Mockito.verify(orderRepository, Mockito.times(1)).findById(anyLong());
    }



    @Test
    @DisplayName("placeOrder- success scenario")
    void placeOrderSuccessScenario(){
        //mock
        Order order = getMockOrder();

        Mockito.when(orderRepository.save(any(Order.class))).thenReturn(order);
        Mockito.when(productService.reduceQuantity(anyLong(), anyLong())).thenReturn(new ResponseEntity<Void>(HttpStatus.OK));
        Mockito.when(paymentService.doPayment(any(PaymentRequest.class))).thenReturn(new ResponseEntity<Long>(order.getId(),HttpStatus.OK));
        //actual
        OrderRequest request = getMockOrderRequest();
        long orderId = orderService.placeOrder(request);

        //assert
        Assertions.assertEquals(order.getId(), orderId);
        //verify
        Mockito.verify(productService, Mockito.times(1)).reduceQuantity(anyLong(), anyLong());
        Mockito.verify(orderRepository, Mockito.times(2)).save(any(Order.class));
        Mockito.verify(paymentService, Mockito.times(1)).doPayment(any(PaymentRequest.class));
    }
    OrderRequest getMockOrderRequest(){
        return OrderRequest.builder()
                .paymentMethod(PaymentMethod.APPLE_PAY)
                .productId(1)
                .quantity(1)
                .totalAmount(1200)
                .build();
    }
    PaymentRequest getPaymentRequest(){
        return new PaymentRequest()
                .builder()
                .orderId(1)
                .paymentMethod(PaymentMethod.APPLE_PAY)
                .amount(1200)
                .build();
    }
    Order getMockOrder(){
        return Order.builder()
                .productId(2)
                .quantity(3)
                .orderDate(Instant.now())
                .orderStatus("accepted")
                .amount(823)
                .build();
    }

    ProductResponse getMockProductResponse(){
        return ProductResponse.builder()
                .productName("zubair")
                .price(888)
                .productName("iphone15")
                .quantity(1)
                .build();
    }



}