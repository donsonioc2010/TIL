package com.jong1.cafekiosk.unit.beverage;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;

class AmericanoTest {

    @Test
    void getName() {
        Americano americano = new Americano();
        assertEquals("아메리카노", americano.getName());
        assertThat(americano.getName()).isEqualTo("아메리카노");
    }

    @Test
    void getPrice() {
        Americano americano = new Americano();
        assertThat(americano.getPrice()).isEqualTo(4_000);
    }
}