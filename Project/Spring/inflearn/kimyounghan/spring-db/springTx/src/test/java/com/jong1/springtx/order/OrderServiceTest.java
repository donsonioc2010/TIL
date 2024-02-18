package com.jong1.springtx.order;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void complete() throws NotEnoughMoneyException {
        //given
        Order order = Order.builder()
            .username("정상")
            .build();

        //when
        orderService.order(order);

        //then
        Assertions.assertEquals("완료", order.getPayStatus());
    }

    @Test
    void bizException() throws NotEnoughMoneyException {
        //given
        Order order = Order.builder()
            .username("잔고부족")
            .build();

        //when
        Assertions.assertThrows(
            NotEnoughMoneyException.class,
            () -> orderService.order(order),
            "잔고 부족 예외 발생"
        );

        //then
        Assertions.assertEquals("대기", order.getPayStatus());
    }

    @Test
    void runtimeException() throws NotEnoughMoneyException {
        //given
        Order order = Order.builder()
            .username("예외")
            .build();

        //when
        Assertions.assertThrows(RuntimeException.class, () -> orderService.order(order), "시스템 예외 발생");

        //then
        Assertions.assertTrue(orderRepository.findById(order.getId()).isEmpty());
    }

}
