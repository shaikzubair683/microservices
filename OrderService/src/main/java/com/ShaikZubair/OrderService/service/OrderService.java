package com.ShaikZubair.OrderService.service;

import com.ShaikZubair.OrderService.model.OrderRequest;

public interface OrderService {

    long placeOrder(OrderRequest orderRequest);
}
