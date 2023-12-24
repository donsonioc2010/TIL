package com.jong1.api;

import com.jong1.domain.Address;
import com.jong1.domain.Order;
import com.jong1.domain.OrderItem;
import com.jong1.domain.OrderStatus;
import com.jong1.repository.OrderRepository;
import com.jong1.vo.OrderSearch;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
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

    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2() {
        return orderRepository.findAllByString(new OrderSearch())
                .stream().map(OrderDto::new)
                .toList();
    }

    @Data
    static class OrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems;

        public OrderDto (Order order) {
            this.orderId = order.getId();
            this.name = order.getMember().getName(); // Lazy 초기화
            this.orderDate = order.getOrderDate();
            this.orderStatus = order.getStatus();
            this.address = order.getDelivery().getAddress(); // Lazy 초기화
            this.orderItems = order.getOrderItems().stream().map(OrderItemDto::new).toList();
        }
    }

    @Data
    static class OrderItemDto {
        private String itemName; // 상품명
        private int orderPrice; // 주문 가격
        private int count; // 주문 수량

        public OrderItemDto (OrderItem orderItem) {
            this.itemName = orderItem.getItem().getName();
            this.orderPrice = orderItem.getOrderPrice();
            this.count = orderItem.getCount();
        }
    }
}
