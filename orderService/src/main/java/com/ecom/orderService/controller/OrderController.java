package com.ecom.orderService.controller;

import com.ecom.orderService.dto.OrderRequest;
import com.ecom.orderService.repository.OrderRepository;
import com.ecom.orderService.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public void placeOrder(@RequestBody OrderRequest orderRequest){
        orderService.placeOrder(orderRequest);
    }

}
