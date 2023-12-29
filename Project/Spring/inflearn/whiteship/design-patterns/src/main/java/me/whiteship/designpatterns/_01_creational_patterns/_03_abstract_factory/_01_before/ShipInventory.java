package me.whiteship.designpatterns._01_creational_patterns._03_abstract_factory._01_before;


import me.whiteship.designpatterns._01_creational_patterns._02_factory_method._02_after.Ship;
import me.whiteship.designpatterns._01_creational_patterns._02_factory_method._02_after.ShipFactory;

public class ShipInventory {

    public static void main(String[] args) {
        ShipFactory shipFactory = new WhiteshipFactory();
        Ship ship = shipFactory.createShip();
    }
}
