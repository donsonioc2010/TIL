package com.jong1.messagestart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

@SpringBootApplication
public class MessageStartApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageStartApplication.class, args);
    }

    /*
    Spring Boot의 경우 MesaageBean을 자동으로 등록하며, profiles에서 컨트롤이 가능하다.
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("messages", "errors");
        messageSource.setDefaultEncoding("utf-8");
        return messageSource;
    }
    */
}
