package com.jong1.cafekiosk.spring.api.service.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;
import com.jong1.cafekiosk.spring.api.service.order.request.OrderCreateServiceRequest;
import com.jong1.cafekiosk.spring.api.service.order.response.OrderResponse;
import com.jong1.cafekiosk.spring.domain.order.repository.OrderRepository;
import com.jong1.cafekiosk.spring.domain.orderproduct.repository.OrderProductRepository;
import com.jong1.cafekiosk.spring.domain.product.entity.Product;
import com.jong1.cafekiosk.spring.domain.product.entity.ProductSellingType;
import com.jong1.cafekiosk.spring.domain.product.entity.ProductType;
import com.jong1.cafekiosk.spring.domain.product.repository.ProductRepository;
import com.jong1.cafekiosk.spring.domain.stock.entity.Stock;
import com.jong1.cafekiosk.spring.domain.stock.repository.StockRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
//@Transactional // 사용을 하게되면, Service에 transactional이 있는것 같은 효과를 줘서 테스트를 진행시 서비스에 적용유무 판단을 못하게 되는 결과가 생길 수 있다. 그러니 사용 안하는 것을 권장.
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

    @Autowired
    private StockRepository stockRepository;
    
    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        stockRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("주문번호 리스트를 받아 주문을 생성한다.")
    void createOrder() {
        // given
        Product product1 = createProduct(ProductType.HANDMADE, "001", 1_000);
        Product product2 = createProduct(ProductType.HANDMADE, "002", 3_000);
        Product product3 = createProduct(ProductType.HANDMADE, "003", 5_000);
        productRepository.saveAll(List.of(product1, product2, product3));

        OrderCreateServiceRequest request = OrderCreateServiceRequest.builder()
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

        OrderCreateServiceRequest request = OrderCreateServiceRequest.builder()
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

    @Test
    @DisplayName("재고와 관련된 상품이 포함되어 있는 주문번호 리스트를 받아 주문을 생성한다.")
    void createOrderWithStock() {
        // given
        Product product1 = createProduct(ProductType.BOTTLE, "001", 1_000);
        Product product2 = createProduct(ProductType.BAKERY, "002", 3_000);
        Product product3 = createProduct(ProductType.HANDMADE, "003", 5_000);
        productRepository.saveAll(List.of(product1, product2, product3));

        Stock stock1 = Stock.create("001", 2);
        Stock stock2 = Stock.create("002", 2);
        stockRepository.saveAll(List.of(stock1, stock2));

        OrderCreateServiceRequest request = OrderCreateServiceRequest.builder()
            .productNumbers(List.of("001", "001", "002", "003"))
            .build();

        // when
        LocalDateTime registeredDateTime = LocalDateTime.now();
        OrderResponse orderResponse = orderService.createOrder(request, registeredDateTime);

        // then
        assertThat(orderResponse.getId()).isNotNull();

        assertThat(orderResponse)
            .extracting("registeredDateTime", "totalPrice")
            .contains(registeredDateTime, 10_000);

        assertThat(orderResponse.getProducts())
            .hasSize(4)
            .extracting("productNumber", "price")
            .containsExactlyInAnyOrder(
                tuple("001", 1_000),
                tuple("001", 1_000),
                tuple("002", 3_000),
                tuple("003", 5_000)
            );

        List<Stock> stocks = stockRepository.findAll();
        assertThat(stocks)
            .hasSize(2)
            .extracting("productNumber", "quantity")
            .containsExactlyInAnyOrder(
                tuple("001", 0),
                tuple("002", 1)
            );
    }


    @Test
    @DisplayName("재고가 부족한 상품으로 주문을 생성하려는 경우 예외가 발생한다..")
    void createOrderWithNoStock() {
        // given
        LocalDateTime registeredDateTime = LocalDateTime.now();

        Product product1 = createProduct(ProductType.BOTTLE, "001", 1_000);
        Product product2 = createProduct(ProductType.BAKERY, "002", 3_000);
        Product product3 = createProduct(ProductType.HANDMADE, "003", 5_000);
        productRepository.saveAll(List.of(product1, product2, product3));

        Stock stock1 = Stock.create("001", 1);
        Stock stock2 = Stock.create("002", 2);
        stockRepository.saveAll(List.of(stock1, stock2));

        OrderCreateServiceRequest request = OrderCreateServiceRequest.builder()
            .productNumbers(List.of("001", "001", "002", "003"))
            .build();

        // when && then
        assertThatThrownBy(() -> orderService.createOrder(request, registeredDateTime))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("재고가 부족한 상품이 있습니다.");
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