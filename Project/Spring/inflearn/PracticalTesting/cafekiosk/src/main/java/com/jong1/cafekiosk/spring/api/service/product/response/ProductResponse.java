package com.jong1.cafekiosk.spring.api.service.product.response;

import com.jong1.cafekiosk.spring.domain.product.entity.Product;
import com.jong1.cafekiosk.spring.domain.product.entity.ProductSellingType;
import com.jong1.cafekiosk.spring.domain.product.entity.ProductType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {
    private Long id;
    private String productNumber;
    private ProductType type;
    private ProductSellingType sellingStatus;
    private String name;
    private int price;

    @Builder
    private ProductResponse(Long id, String productNumber, ProductType type,
        ProductSellingType sellingStatus, String name, int price) {
        this.id = id;
        this.productNumber = productNumber;
        this.type = type;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }

    public static ProductResponse of(Product product) {
        return ProductResponse.builder()
            .id(product.getId())
            .productNumber(product.getProductNumber())
            .type(product.getType())
            .sellingStatus(product.getSellingStatus())
            .name(product.getName())
            .price(product.getPrice())
            .build();
    }
}
