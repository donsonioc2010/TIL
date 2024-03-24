package com.jong1.cafekiosk.spring.domain.product.entity;

import static org.junit.jupiter.api.Assertions.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTypeTest {

    @Test
    @DisplayName("상품 타입이 재고를 확인하는 관련 타입인지를 체크한다.")
    void containsStockType() {
        // given
        ProductType givenType = ProductType.HANDMADE;

        // when
        boolean result =ProductType.containsStockType(givenType);

        // then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    @DisplayName("상품 타입이 재고를 확인하는 관련 타입인지를 체크한다.")
    void containsStockType2() {
        // given
        ProductType givenType = ProductType.BAKERY;

        // when
        boolean result =ProductType.containsStockType(givenType);

        // then
        Assertions.assertThat(result).isTrue();
    }
}