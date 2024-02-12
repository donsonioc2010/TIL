package com.jong1.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class CheckedTest {

    @Test
    void checkedCatch() {
        Service service = new Service();
        service.callCatch();
    }

    @Test
    void checkedThrow() {
        Service service = new Service();
        Assertions.assertThrows(MycheckedException.class, () -> service.callThrow());
    }

    // Exception을 상속받기 떄문에 체크 예외가 된다.
    static class MycheckedException extends Exception {

        public MycheckedException(String message) {
            super(message);
        }
    }

    static class Service {

        Repository repo = new Repository();

        /**
         * 예외를 잡아 처리하는 코드
         */
        public void callCatch() {
            try {
                repo.call();
            }
            catch (MycheckedException e) {
                log.info("예외처리 >>> {}", e.getMessage(), e);
            }
        }

        // 체크 예외를 밖으로 던지는 코드
        public void callThrow() throws MycheckedException {
            repo.call();
        }
    }

    static class Repository {

        // Checked는 반드시 Catch를 하거나 던져야한다.
        public void call() throws MycheckedException {
            throw new MycheckedException("Repository Exception");
        }
    }
}
