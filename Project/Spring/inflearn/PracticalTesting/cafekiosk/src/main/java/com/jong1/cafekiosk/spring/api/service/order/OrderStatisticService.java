package com.jong1.cafekiosk.spring.api.service.order;


import com.jong1.cafekiosk.spring.api.service.mail.MailService;
import com.jong1.cafekiosk.spring.domain.order.entity.Order;
import com.jong1.cafekiosk.spring.domain.order.entity.OrderStatus;
import com.jong1.cafekiosk.spring.domain.order.repository.OrderRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderStatisticService {

    private final OrderRepository orderRepository;
    private final MailService mailService;

    public boolean sendOrderStatisticsMail(LocalDate orderDate, String email) {
        // 해당 일자에 결제 완료된 주문들을 가져와서
        List<Order> orders = orderRepository.findOrderBy(
            orderDate.atStartOfDay(),
            orderDate.plusDays(1).atStartOfDay(),
            OrderStatus.PAYMENT_COMPLETED
        );

        // 총 매출 합계를 계한후
        int totalAmount = orders.stream()
            .mapToInt(Order::getTotalPrice)
            .sum();

        // 메일발송을 한다
        // 발송자, 수신자, 제목, 내용
        boolean result = mailService.sendMail(
            "no-reply@cafekiosk.com",
            email,
            String.format("[매출통계] %s", orderDate),
            String.format("총 매출 합계는 %s원 입니다.", totalAmount)
        );

        if (!result) {
            throw new IllegalArgumentException("매출 통계 메일 전송에 실패했습니다.");
        }

        return Boolean.TRUE;
    }
}
