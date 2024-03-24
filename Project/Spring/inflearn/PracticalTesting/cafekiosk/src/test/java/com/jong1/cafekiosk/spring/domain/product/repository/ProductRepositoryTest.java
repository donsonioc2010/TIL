package com.jong1.cafekiosk.spring.domain.product.repository;

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
        Product product1 = Product.builder()
            .productNumber("001")
            .type(ProductType.HANDMADE)
            .sellingStatus(ProductSellingType.SELLING)
            .name("아메리카노")
            .price(4_000)
            .build();
        Product product2 = Product.builder()
            .productNumber("002")
            .type(ProductType.HANDMADE)
            .sellingStatus(ProductSellingType.HOLD)
            .name("카페라떼")
            .price(4_500)
            .build();
        Product product3 = Product.builder()
            .productNumber("003")
            .type(ProductType.HANDMADE)
            .sellingStatus(ProductSellingType.STOP_SELLING)
            .name("팥빙수")
            .price(7_000)
            .build();

        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        List<Product> products = productRepository.findAllBySellingStatusIn(
            List.of(ProductSellingType.SELLING, ProductSellingType.HOLD));

        // then
        assertThat(products)
            .hasSize(2)
            .extracting("productNumber", "name", "sellingStatus")
            .containsExactlyInAnyOrder(
                tuple("001", "아메리카노", ProductSellingType.SELLING),
                tuple("002", "카페라떼", ProductSellingType.HOLD)
            );
    }
}