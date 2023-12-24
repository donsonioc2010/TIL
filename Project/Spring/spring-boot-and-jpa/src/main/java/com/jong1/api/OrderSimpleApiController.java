package com.jong1.api;

import com.jong1.domain.Order;
import com.jong1.repository.OrderRepository;
import com.jong1.repository.order.simplequery.OrderSimpleQueryDto;
import com.jong1.repository.order.simplequery.OrderSimpleQueryRepository;
import com.jong1.vo.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 컬렉션이 아닌 OneToOne, ManyToOne의 관계에 대해서만 성능최적화를 어떻게 하느지에 대한 내용
 *
 * Order를 조회
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    // 설정이 없는 경우에는, Jackson에서 JSON변환시 순환참조 발생!
    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> orders =  orderRepository.findAllByString(new OrderSearch());
        for (Order order : orders) {
            order.getMember().getName(); // Lazy 강제 초기화
            order.getDelivery().getAddress(); // Lazy 강제 초기화
        }

        return orders;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<OrderSimpleQueryDto> orderV2() {
        return orderRepository.findAllByString(new OrderSearch())
                .stream()
                .map(OrderSimpleQueryDto::new)
                .toList();
    }

    @GetMapping("/api/v3/simple-orders")
    public List<OrderSimpleQueryDto> orderV3() {
        return orderRepository.findAllMemberDelivery()
                .stream()
                .map(OrderSimpleQueryDto::new)
                .toList();
    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> orderV4() {
        return orderSimpleQueryRepository.findOrderDtos();
    }

}
