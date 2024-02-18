package com.jong1.springtx.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    @Transactional
    public void order(Order order) throws NotEnoughMoneyException {
        log.info("Call OrderService.order");
        orderRepository.save(order);

        log.info("결제 로직 수행");
        if("예외".equals(order.getUsername())) {
            log.info("시스템 예외 발생");
            throw new RuntimeException("시스템 예외 발생");
        } else if("잔고부족".equals(order.getUsername())) {
            log.info("잔고 부족 예외 발생");
            order.setPayStatus("대기");
            throw new NotEnoughMoneyException("잔고 부족 예외 발생");
        } else {
            log.info("결제 완료");
            order.setPayStatus("완료");
        }

        log.info("결제 프로세스 완료");
    }

}
