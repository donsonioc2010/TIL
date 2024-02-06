package com.jong1.typeconverter.formatter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class MyNumberFormatterTest {

    MyNumberFormatter formatter = new MyNumberFormatter();
    @Test
    void parse() throws ParseException {
        Assertions.assertEquals(formatter.parse("1,000", Locale.KOREA), 1000L);
    }

    @Test
    void print() {
        Assertions.assertEquals(formatter.print(1000, Locale.KOREA), "1,000");
    }
}