package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AppTest {
    private final App app = new App();

    @Test
    public void testApp() {
        assertTrue( true );
    }

    @Test
    public void testHelloWorld() {
        assertEquals("Hello World!", app.helloWorld());
    }


}
