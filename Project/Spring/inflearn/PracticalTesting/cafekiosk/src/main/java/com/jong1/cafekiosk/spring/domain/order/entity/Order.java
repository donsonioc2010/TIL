package com.jong1.cafekiosk.spring.domain.order.entity;


import com.jong1.cafekiosk.spring.domain.BaseEntity;
import com.jong1.cafekiosk.spring.domain.orderproduct.entity.OrderProduct;
import com.jong1.cafekiosk.spring.domain.product.entity.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Comment("주문")
@Table(name = "tbl_orders")
public class Order extends BaseEntity {

    @Id
    @Comment("주문 ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("주문 상태")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Comment("주문 총 금액")
    private int totalPrice;

    @Comment("주문 등록 시간")
    private LocalDateTime registeredDateTime;

    // Order가 생성되거나, 변경되거나 할때 같이 변경되도록 생명주기를 설정
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    public Order(List<Product> products, LocalDateTime registeredDateTime) {
        this.orderStatus = OrderStatus.INIT;
        this.totalPrice = calculateTotalPrice(products);
        this.registeredDateTime = registeredDateTime;
        this.orderProducts = products.stream()
            .map(product -> new OrderProduct(this, product))
            .toList();
    }


    public static Order create(List<Product> products, LocalDateTime registeredDateTime) {
        return new Order(products, registeredDateTime);
    }


    // === 기능 메서드 ===
    private int calculateTotalPrice(List<Product> products) {
        return products.stream()
            .mapToInt(Product::getPrice)
            .sum();
    }

}
