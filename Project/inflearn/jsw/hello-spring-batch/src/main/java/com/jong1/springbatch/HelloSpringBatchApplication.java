package com.jong1.springbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableBatchProcessing
//@EnableScheduling
public class HelloSpringBatchApplication {

    public static void main(String[] args) {
        for (String arg : args) {
            System.out.println("arg: " + arg);
        }
        SpringApplication.run(HelloSpringBatchApplication.class, args);
    }

}
