package com.jong1.cafekiosk.spring.api.service.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import com.jong1.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import com.jong1.cafekiosk.spring.api.service.order.response.OrderResponse;
import com.jong1.cafekiosk.spring.domain.order.repository.OrderRepository;
import com.jong1.cafekiosk.spring.domain.orderproduct.repository.OrderProductRepository;
import com.jong1.cafekiosk.spring.domain.product.entity.Product;
import com.jong1.cafekiosk.spring.domain.product.entity.ProductSellingType;
import com.jong1.cafekiosk.spring.domain.product.entity.ProductType;
import com.jong1.cafekiosk.spring.domain.product.repository.ProductRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
//@DataJpaTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;
    
    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("주문번호 리스트를 받아 주문을 생성한다.")
    void createOrder() {
        // given
        Product product1 = createProduct(ProductType.HANDMADE, "001", 1_000);
        Product product2 = createProduct(ProductType.HANDMADE, "002", 3_000);
        Product product3 = createProduct(ProductType.HANDMADE, "003", 5_000);
        productRepository.saveAll(List.of(product1, product2, product3));

        OrderCreateRequest request = OrderCreateRequest.builder()
            .productNumbers(List.of("001", "002"))
            .build();

        // when
        LocalDateTime registeredDateTime = LocalDateTime.now();
        OrderResponse orderResponse = orderService.createOrder(request, registeredDateTime);

        // then
        assertThat(orderResponse.getId()).isNotNull();

        assertThat(orderResponse)
            .extracting("registeredDateTime", "totalPrice")
            .contains(registeredDateTime, 4000);

        assertThat(orderResponse.getProducts())
            .hasSize(2)
            .extracting("productNumber", "price")
            .containsExactlyInAnyOrder(
                tuple("001", 1000),
                tuple("002", 3000)
            );
    }

    @Test
    @DisplayName("중복되는 상품 번호 리스트로 주문을 생성 할 수 있다.")
    void createOrderWithDuplicateProductNumbers() {
        // given
        Product product1 = createProduct(ProductType.HANDMADE, "001", 1_000);
        Product product2 = createProduct(ProductType.HANDMADE, "002", 3_000);
        Product product3 = createProduct(ProductType.HANDMADE, "003", 5_000);
        productRepository.saveAll(List.of(product1, product2, product3));

        OrderCreateRequest request = OrderCreateRequest.builder()
            .productNumbers(List.of("001", "001"))
            .build();

        // when
        LocalDateTime registeredDateTime = LocalDateTime.now();
        OrderResponse orderResponse = orderService.createOrder(request, registeredDateTime);

        // then
        assertThat(orderResponse.getId()).isNotNull();

        assertThat(orderResponse)
            .extracting("registeredDateTime", "totalPrice")
            .contains(registeredDateTime, 2000);

        assertThat(orderResponse.getProducts())
            .hasSize(2)
            .extracting("productNumber", "price")
            .containsExactlyInAnyOrder(
                tuple("001", 1000),
                tuple("001", 1000)
            );
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