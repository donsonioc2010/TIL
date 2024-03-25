package com.jong1.cafekiosk.spring.api.controller.product.dto.request;

import com.jong1.cafekiosk.spring.domain.product.entity.Product;
import com.jong1.cafekiosk.spring.domain.product.entity.ProductSellingType;
import com.jong1.cafekiosk.spring.domain.product.entity.ProductType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductCreateRequest {
    private ProductType type;
    private ProductSellingType sellingStatus;
    private String name;
    private int price;

    @Builder
    private ProductCreateRequest(ProductType type, ProductSellingType sellingStatus, String name,
        int price) {
        this.type = type;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }

    public Product toEntity(String nextProductNumber) {
        return Product.builder()
            .productNumber(nextProductNumber)
            .type(type)
            .sellingStatus(sellingStatus)
            .name(name)
            .price(price)
            .build();
    }
}
