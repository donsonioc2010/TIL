package com.jong1.cafekiosk.unit;

import static org.junit.jupiter.api.Assertions.*;
import com.jong1.cafekiosk.unit.beverage.Americano;
import org.junit.jupiter.api.Test;

class CafeKioskTest {
    @Test
    void add() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        System.out.println(">>> 담긴 음로 수 : " + cafeKiosk.getBeverages().size());
        System.out.println(">>> 담긴 음로 : " + cafeKiosk.getBeverages().get(0).getName());
    }
}