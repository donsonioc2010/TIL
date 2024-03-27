package com.jong1.cafekiosk.spring.domain.product.repository;

import static com.jong1.cafekiosk.spring.domain.product.entity.ProductSellingType.HOLD;
import static com.jong1.cafekiosk.spring.domain.product.entity.ProductSellingType.SELLING;
import static com.jong1.cafekiosk.spring.domain.product.entity.ProductSellingType.STOP_SELLING;
import static com.jong1.cafekiosk.spring.domain.product.entity.ProductType.HANDMADE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import com.jong1.cafekiosk.spring.domain.product.entity.Product;
import com.jong1.cafekiosk.spring.domain.product.entity.ProductSellingType;
import com.jong1.cafekiosk.spring.domain.product.entity.ProductType;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test") // profiles를 test로 설정
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("원하는 판매상태를 가진 상품들을 조회한다.")
    void findAllBySellingStatusIn() {
        // given
        Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4_000);
        Product product2 = createProduct("002", HANDMADE, HOLD, "카페라떼", 4_500);
        Product product3 = createProduct("003", HANDMADE, STOP_SELLING, "팥빙수",7_000);

        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        List<Product> products = productRepository.findAllBySellingStatusIn(
            List.of(SELLING, HOLD));

        // then
        assertThat(products)
            .hasSize(2)
            .extracting("productNumber", "name", "sellingStatus")
            .containsExactlyInAnyOrder(
                tuple("001", "아메리카노", SELLING),
                tuple("002", "카페라떼", HOLD)
            );
    }

    @Test
    @DisplayName("상품번호 리스트로 상품들을 조회한다.")
    void findAllByProductNumberIn() {
        // given
        Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4_000);
        Product product2 = createProduct("002", HANDMADE, HOLD, "카페라떼", 4_500);
        Product product3 = createProduct("003", HANDMADE, STOP_SELLING, "팥빙수", 7_000);

        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        List<Product> products = productRepository.findAllByProductNumberIn(List.of("001", "002"));

        // then
        assertThat(products)
            .hasSize(2)
            .extracting("productNumber", "name", "sellingStatus")
            .containsExactlyInAnyOrder(
                tuple("001", "아메리카노", SELLING),
                tuple("002", "카페라떼", HOLD)
            );
    }

    @Test
    @DisplayName("가장 마지막으로 저장한 상품의 상품번호를 읽어온다.")
    void findLatestProductNumber() {
        // given
        Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4_000);
        Product product2 = createProduct("002", HANDMADE, HOLD, "카페라떼", 4_500);

        String targetProductNumber = "003";
        Product product3 = createProduct(targetProductNumber, HANDMADE, STOP_SELLING, "팥빙수", 7_000);

        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        String latestProductNumber = productRepository.findLatestProduct();

        // then
        assertThat(latestProductNumber)
            .isNotNull()
            .isEqualTo(targetProductNumber);
    }

    @Test
    @DisplayName("가장 마지막으로 저장한 상품의 상품번호를 읽어올 때, 상품이 하나도 없는 경우에는 Null을 읽어온다.")
    void findLatestProductNumberWhenProductIsEmpty() {
        // given

        // when
        String latestProductNumber = productRepository.findLatestProduct();

        // then
        assertThat(latestProductNumber)
            .isNull();
    }

    private Product createProduct(String productNumber, ProductType type,
        ProductSellingType sellingStatus, String name, int price) {
        return Product.builder()
            .productNumber(productNumber)
            .type(type)
            .sellingStatus(sellingStatus)
            .name(name)
            .price(price)
            .build();
    }
}