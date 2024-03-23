package com.jong1.cafekiosk.unit;

import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import com.jong1.cafekiosk.unit.beverage.Americano;
import com.jong1.cafekiosk.unit.beverage.Latte;
import com.jong1.cafekiosk.unit.order.Order;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class CafeKioskTest {

    @Test
    void add_manual_test() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        System.out.println(">>> 담긴 음로 수 : " + cafeKiosk.getBeverages().size());
        System.out.println(">>> 담긴 음로 : " + cafeKiosk.getBeverages().get(0).getName());
    }

    @Test
    void add() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        assertThat(cafeKiosk.getBeverages()).hasSize(1);
        assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @Test
    void addSeveralBeverages() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano, 2);

        assertThat(cafeKiosk.getBeverages().get(0)).isEqualTo(americano);
        assertThat(cafeKiosk.getBeverages().get(1)).isEqualTo(americano);
    }


    @Test
    void addZeroBeverages() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        assertThatThrownBy(() -> cafeKiosk.add(americano, 0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("음료는 1잔 이상 주문하실 수 있습니다.");
    }

    @Test
    void remove() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano);
        cafeKiosk.remove(americano);

        assertThat(cafeKiosk.getBeverages()).isEmpty();
    }

    @Test
    void clear() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());
        cafeKiosk.add(new Latte());
        assertThat(cafeKiosk.getBeverages()).hasSize(2);

        cafeKiosk.clear();
        assertThat(cafeKiosk.getBeverages()).isEmpty();
    }

    @Test
    void calculateTotalPrice() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());
        cafeKiosk.add(new Latte());

        assertThat(cafeKiosk.calculateTotalPrice()).isEqualTo(8_500);
    }

    @Test
    void createOrder() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);

        // 기존의 기능은 현재시간에 테스트 결과가 의존된다
        Order order = cafeKiosk.createOrder();

        assertThat(order.getBeverages()).hasSize(1);
        assertThat(order.getBeverages().get(0)).isEqualTo(americano);
        assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @Test
    void createOrderWithMinCurrentTime() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);

        Order order = cafeKiosk.createOrder(of(2024, 3, 23, 10, 0));

        assertThat(order.getBeverages()).hasSize(1);
        assertThat(order.getBeverages().get(0)).isEqualTo(americano);
        assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @Test
    void createOrderWithMaxCurrentTime() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);

        Order order = cafeKiosk.createOrder(of(2024, 3, 23, 22, 0));

        assertThat(order.getBeverages()).hasSize(1);
        assertThat(order.getBeverages().get(0)).isEqualTo(americano);
        assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @Test
    void createOrderOutsideOpentime() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);

        assertThatThrownBy(() -> {
            cafeKiosk.createOrder(of(2024, 3, 23, 9, 59));
        })
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("주문 시간이 아닙니다. 관리자에게 문의하세요.");
    }
}