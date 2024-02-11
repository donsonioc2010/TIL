package com.jong1.jdbc.connection;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.jong1.jdbc.connection.ConnectionConst.PASSWORD;
import static com.jong1.jdbc.connection.ConnectionConst.URL;
import static com.jong1.jdbc.connection.ConnectionConst.USERNAME;

@Slf4j
public class ConnectionTest {
    @Test
    void driverManager() throws SQLException {
        Connection conn1 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Connection conn2 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        log.info("conn1 >>> {}, class >>> {}", conn1, conn1.getClass());
        log.info("conn2 >>> {}, class >>> {}", conn2, conn2.getClass());
    }

    // DataSource를 사용하는 경우 Connection을 연결할 때 마다 DB의 정보를 설정하지 않아도 된다.
    private void useDatasource(DataSource datasource) throws SQLException {
        Connection dcon1 = datasource.getConnection();
        Connection dcon2 = datasource.getConnection();

        log.info("conn1 >>> {}, class >>> {}", dcon1, dcon1.getClass());
        log.info("conn2 >>> {}, class >>> {}", dcon2, dcon2.getClass());
    }

    @Test
    void dataSourceDriverManager() throws SQLException {
        // DriverManager를 이용한 Connection 생성 - 매번 새로운 Connection을 획득한다.
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        useDatasource(dataSource);
    }

    @Test
    void dataSourceConnectionPool() throws SQLException {
        // Connection Pooling
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaximumPoolSize(10);
        dataSource.setMinimumIdle(2);
        dataSource.setPoolName("MyPool");

        useDatasource(dataSource);
    }

}
