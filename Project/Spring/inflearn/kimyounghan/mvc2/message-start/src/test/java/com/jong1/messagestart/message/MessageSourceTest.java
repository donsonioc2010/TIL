package com.jong1.messagestart.message;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class MessageSourceTest {
    @Autowired
    private MessageSource ms;

    @Test
    void helloMessage() {
        String result = ms.getMessage("hello", null, null);
        assertEquals("안녕", result);
    }

    @Test
    void notFoundMessageCode() {
        /*못찾는 경우 NoSuchMessageException이 발생한다.*/
        assertThrows(NoSuchMessageException.class, () -> ms.getMessage("no_code", null, null));
    }

    @Test
    void notFoundMessageCodeDefaultMessage() {
        /*못찾는 경우 NoSuchMessageException이 발생한다.*/
        String result = ms.getMessage("no_code", null, "기본 메세지", null);
        assertEquals("기본 메세지", result);
    }

    @Test
    void argumentMessage() {
        String result = ms.getMessage("hello.name", new Object[]{"Spring"}, null);
        assertEquals("안녕 Spring", result);
    }

    @Test
    void defaultLang() {
        assertEquals("안녕", ms.getMessage("hello", null, null));
        assertEquals("안녕", ms.getMessage("hello", null, Locale.KOREA));
    }

    @Test
    void enLang() {
        assertEquals("hello", ms.getMessage("hello", null, Locale.ENGLISH));
    }
}
