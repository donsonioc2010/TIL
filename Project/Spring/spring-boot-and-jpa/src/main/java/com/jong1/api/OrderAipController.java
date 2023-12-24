package com.jong1.api;

import com.jong1.domain.Order;
import com.jong1.repository.OrderRepository;
import com.jong1.vo.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderAipController {
    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        all.forEach(o-> {
            o.getMember().getName(); // Lazy 초기화
            o.getDelivery().getAddress(); // Lazy 초기화
            o.getOrderItems().forEach(oi -> oi.getItem().getName()); // Lazy 초기화
        });
        return all;
    }
}
