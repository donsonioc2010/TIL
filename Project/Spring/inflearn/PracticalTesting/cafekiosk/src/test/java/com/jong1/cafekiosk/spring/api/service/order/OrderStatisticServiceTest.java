package com.jong1.cafekiosk.spring.api.service.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import com.jong1.cafekiosk.spring.IntegrationTestSupport;
import com.jong1.cafekiosk.spring.client.mail.MailSendClient;
import com.jong1.cafekiosk.spring.domain.history.mail.entity.MailSendHistory;
import com.jong1.cafekiosk.spring.domain.history.mail.repository.MailSendHistoryRepository;
import com.jong1.cafekiosk.spring.domain.order.entity.Order;
import com.jong1.cafekiosk.spring.domain.order.entity.OrderStatus;
import com.jong1.cafekiosk.spring.domain.order.repository.OrderRepository;
import com.jong1.cafekiosk.spring.domain.orderproduct.repository.OrderProductRepository;
import com.jong1.cafekiosk.spring.domain.product.entity.Product;
import com.jong1.cafekiosk.spring.domain.product.entity.ProductSellingType;
import com.jong1.cafekiosk.spring.domain.product.entity.ProductType;
import com.jong1.cafekiosk.spring.domain.product.repository.ProductRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
//@ActiveProfiles("test")
//@SpringBootTest
class OrderStatisticServiceTest extends IntegrationTestSupport {
    @Autowired
    private OrderStatisticService orderStatisticService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MailSendHistoryRepository mailSendHistoryRepository;

//    @MockBean
//    private MailSendClient mailSendClient;

    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        mailSendHistoryRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("결제완료 주문들을 조회하여 매출 통계 메일을 작성한다.")
    void testCaseName() {
        // given
        LocalDateTime today = LocalDateTime.of(2024, 3, 27, 0, 0);
        Product product1 = createProduct(ProductType.HANDMADE, "001", 1_000);
        Product product2 = createProduct(ProductType.HANDMADE, "002", 3_000);
        Product product3 = createProduct(ProductType.HANDMADE, "003", 5_000);
        List<Product> products = List.of(product1, product2, product3);
        productRepository.saveAll(products);

        // order2와 3만 걸려야함.
        Order order = createPaymentCompletedOrder(LocalDateTime.of(2024, 3, 26, 23, 59, 59), products);
        Order order2 = createPaymentCompletedOrder(today, products);
        Order order3 = createPaymentCompletedOrder(LocalDateTime.of(2024, 3, 27, 23, 59, 59), products);
        Order order4 = createPaymentCompletedOrder(LocalDateTime.of(2024, 3, 28, 0, 0), products);

        Mockito.when((mailSendClient.sendMail(any(String.class), any(String.class), any(String.class), any(String.class))))
            .thenReturn(true);

        // when
        boolean result = orderStatisticService.sendOrderStatisticsMail(today.toLocalDate(), "test@test.com");

        // then
        assertThat(result).isTrue();

        List<MailSendHistory> mailSendHistories = mailSendHistoryRepository.findAll();
        assertThat(mailSendHistories).hasSize(1)
            .extracting(MailSendHistory::getContent)
            .contains("총 매출 합계는 18000원 입니다.");
    }

    private Order createPaymentCompletedOrder(LocalDateTime today, List<Product> products) {
        Order order = Order.builder()
            .orderStatus(OrderStatus.PAYMENT_COMPLETED)
            .products(products)
            .registeredDateTime(today)
            .build();
        return orderRepository.save(order);
    }

    private Product createProduct(ProductType type, String productNumber, int price) {
        return Product.builder()
            .productNumber(productNumber)
            .type(type)
            .sellingStatus(ProductSellingType.SELLING)
            .name("메뉴 이름")
            .price(price)
            .build();
    }
}