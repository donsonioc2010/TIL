package com.jong1.cafekiosk.spring.api.service.product;

import static com.jong1.cafekiosk.spring.domain.product.entity.ProductSellingType.HOLD;
import static com.jong1.cafekiosk.spring.domain.product.entity.ProductSellingType.SELLING;
import static com.jong1.cafekiosk.spring.domain.product.entity.ProductSellingType.STOP_SELLING;
import static com.jong1.cafekiosk.spring.domain.product.entity.ProductType.HANDMADE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;
import com.jong1.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import com.jong1.cafekiosk.spring.api.service.product.response.ProductResponse;
import com.jong1.cafekiosk.spring.domain.product.entity.Product;
import com.jong1.cafekiosk.spring.domain.product.entity.ProductSellingType;
import com.jong1.cafekiosk.spring.domain.product.entity.ProductType;
import com.jong1.cafekiosk.spring.domain.product.repository.ProductRepository;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void tearDown() {
        productRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("신규 상품을 등록한다. 상품번호는 가장 최근 상품의 상품번호에서 1 증가한 값이다.")
    void createProduct() {
        // given
        Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4_000);
        productRepository.save(product1);

        ProductCreateRequest request = ProductCreateRequest.builder()
            .type(HANDMADE)
            .sellingStatus(SELLING)
            .name("카푸치노")
            .price(5_000)
            .build();

        // when
        ProductResponse productResponse = productService.createProduct(request);

        // then
        assertThat(productResponse)
            .isNotNull()
            .extracting("productNumber", "type", "sellingStatus", "name", "price")
            .contains("002", HANDMADE, SELLING, "카푸치노", 5_000);

        List<Product> products = productRepository.findAll();
        assertThat(products)
            .hasSize(2)
            .extracting("productNumber", "type", "sellingStatus", "name", "price")
            .containsExactlyInAnyOrder(
                tuple("001", HANDMADE, SELLING, "아메리카노", 4_000),
                tuple("002", HANDMADE, SELLING, "카푸치노", 5_000)
            );
    }

    @Test
    @DisplayName("상품이 하나도 없는 경우 신규 상품을 등록하면 상품 번호는 001 이다.")
    void createProductWhenProductsIsEmpty() {
        // given
        ProductCreateRequest request = ProductCreateRequest.builder()
            .type(HANDMADE)
            .sellingStatus(SELLING)
            .name("카푸치노")
            .price(5_000)
            .build();

        // when
        ProductResponse productResponse = productService.createProduct(request);

        // then
        assertThat(productResponse)
            .isNotNull()
            .extracting("productNumber", "type", "sellingStatus", "name", "price")
            .contains("001", HANDMADE, SELLING, "카푸치노", 5_000);

        List<Product> products = productRepository.findAll();
        assertThat(products)
            .hasSize(1)
            .extracting("productNumber", "type", "sellingStatus", "name", "price")
            .containsExactlyInAnyOrder(
                tuple("001", HANDMADE, SELLING, "카푸치노", 5_000)
            );
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