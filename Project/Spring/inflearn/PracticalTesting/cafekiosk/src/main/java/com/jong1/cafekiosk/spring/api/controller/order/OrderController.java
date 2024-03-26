package com.jong1.cafekiosk.spring.api.controller.order;

import com.jong1.cafekiosk.spring.api.ApiResponse;
import com.jong1.cafekiosk.spring.api.service.order.OrderService;
import com.jong1.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import com.jong1.cafekiosk.spring.api.service.order.response.OrderResponse;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/api/v1/orders/new")
    public ApiResponse<OrderResponse> createOrder(@RequestBody @Validated OrderCreateRequest request) {
        LocalDateTime registeredDateTime = LocalDateTime.now();
        return ApiResponse.ok(orderService.createOrder(request.toServiceRequest(), registeredDateTime));
    }
}
