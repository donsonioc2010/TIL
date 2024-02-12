package com.jong1.jdbc.exception.basic;

import java.net.ConnectException;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class CheckedAppTest {

    @Test
    void checked() {
        Controller Controller = new Controller();
        Assertions.assertThrows(Exception.class, () -> Controller.request());
    }


    static class Controller {
        Service service = new Service();

        public void request() throws SQLException, ConnectException {
            service.logic();
        }
    }

    static class Service {

        Repository repository = new Repository();
        NetworkClient networkClient = new NetworkClient();

        public void logic() throws ConnectException, SQLException {
            repository.call();
            networkClient.call();
        }
    }

    static class NetworkClient {

        public void call() throws ConnectException {
            throw new ConnectException("연결 실패");
        }
    }

    static class Repository {

        public void call() throws SQLException {
            throw new SQLException("EX");
        }
    }


}
