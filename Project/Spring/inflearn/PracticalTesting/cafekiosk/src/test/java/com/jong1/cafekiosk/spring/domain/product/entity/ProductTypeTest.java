package com.jong1.cafekiosk.spring.domain.product.entity;

import static org.junit.jupiter.api.Assertions.*;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

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


    @ParameterizedTest
    @DisplayName("상품 타입이 재고를 확인하는 관련 타입인지를 체크한다.")
    @CsvSource({"HANDMADE, false", "BOTTLE, true", "BAKERY, true"})
    void containsStockTyp3(ProductType productType, boolean expected) {
        // given


        // when
        boolean result = ProductType.containsStockType(productType);

        // then
        Assertions.assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> provideProductTypesForCheckingStockType () {
        return Stream.of(
            Arguments.of(ProductType.HANDMADE, false),
            Arguments.of(ProductType.BOTTLE, true),
            Arguments.of(ProductType.BAKERY, true)
        );
    }

    @ParameterizedTest
    @DisplayName("상품 타입이 재고를 확인하는 관련 타입인지를 체크한다.")
    @MethodSource("provideProductTypesForCheckingStockType")
    void containsStockTyp4(ProductType productType, boolean expected) {
        // given

        // when
        boolean result = ProductType.containsStockType(productType);

        // then
        Assertions.assertThat(result).isEqualTo(expected);
    }
}