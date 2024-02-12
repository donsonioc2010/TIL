package com.jong1.jdbc.exception.basic;

import java.net.ConnectException;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class UnCheckedAppTest {

    @Test
    void unchecked() {
        Controller Controller = new Controller();
        Assertions.assertThrows(RuntimeException.class, () -> Controller.request());
    }


    static class Controller {
        Service service = new Service();

        public void request() {
            service.logic();
        }
    }

    static class Service {

        Repository repository = new Repository();
        NetworkClient networkClient = new NetworkClient();

        public void logic() {
            repository.call();
            networkClient.call();
        }
    }

    static class NetworkClient {
        public void call() {
            try {
                runConnect();
            }
            catch (ConnectException e) {
                // cause를 담아서 던져야, 이전에 어떤 예외가 발생했는지 확인이 가능하다.
                throw new RuntimeConnectException(e);
            }
        }

        private void runConnect() throws ConnectException {
            throw new ConnectException("연결 실패");
        }
    }

    static class Repository {

        public void call() {
            try {
                runSQL();
            }
            catch (SQLException e) {
                // cause를 담아서 던져야, 이전에 어떤 예외가 발생했는지 확인이 가능하다.
                throw new RuntimeSQLException(e);
            }
        }

        private void runSQL() throws SQLException {
            throw new SQLException("EX");
        }
    }

    static class RuntimeConnectException extends RuntimeException {

        public RuntimeConnectException(String message) {
            super(message);
        }

        public RuntimeConnectException(Throwable cause) {
            super(cause);
        }
    }

    static class RuntimeSQLException extends RuntimeException {

        public RuntimeSQLException(String message) {
            super(message);
        }

        public RuntimeSQLException(Throwable cause) {
            super(cause);
        }
    }

}
