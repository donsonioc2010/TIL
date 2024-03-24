package com.jong1.cafekiosk.spring.domain.product.entity;

import com.jong1.cafekiosk.spring.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Comment("상품")
@Table(name = "tbl_product")
public class Product extends BaseEntity {
    @Id
    @Comment("상품 ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("상품 번호")
    private String productNumber;

    @Comment("상품 타입")
    @Enumerated(EnumType.STRING)
    private ProductType type;

    @Comment("판매 상태")
    @Enumerated(EnumType.STRING)
    private ProductSellingType sellingStatus;

    @Comment("상품 명")
    private String name;

    @Comment("상품 가격")
    private int price;

    @Builder
    public Product(String productNumber, ProductType type, ProductSellingType sellingStatus,
        String name, int price) {
        this.productNumber = productNumber;
        this.type = type;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }
}
