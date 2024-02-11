package com.jong1.jdbc;

import com.jong1.jdbc.connection.DBConnectionUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JdbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(JdbcApplication.class, args);
        DBConnectionUtil.getConnection();
    }

}
