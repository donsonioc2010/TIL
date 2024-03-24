package com.jong1.cafekiosk.spring.domain.orderproduct.entity;

import com.jong1.cafekiosk.spring.domain.BaseEntity;
import com.jong1.cafekiosk.spring.domain.order.entity.Order;
import com.jong1.cafekiosk.spring.domain.product.entity.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Comment("주문 상품목록")
@Entity
@Table(name = "tbl_order_products")
public class OrderProduct extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("주문정보")
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @Comment("상품정보")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    public OrderProduct(Order order, Product product) {
        this.order = order;
        this.product = product;
    }
}
