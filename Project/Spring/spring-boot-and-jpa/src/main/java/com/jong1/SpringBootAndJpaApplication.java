package com.jong1;

import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootAndJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAndJpaApplication.class, args);
    }

    @Bean
    Hibernate5JakartaModule hibernate5JakartaModule() {
        return new Hibernate5JakartaModule(); //기본적으로 초기화 된 프록시 객체만 노출, 초기화 되지 않은 프록시 객체(지연로딩된 객체)는 노출 안함
    /*
        Hibernate5JakartaModule hibernate5Module = new Hibernate5JakartaModule();
        hibernate5Module.configure(Hibernate5JakartaModule.Feature.FORCE_LAZY_LOADING, true); //초기화 되지 않은 프록시 객체(LAZY로, 호출되지 않은 객체)도 강제로 초기화 시킴
        return hibernate5Module;
    */
    }

}
