package com.jong1.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class UnCheckedTest {

    @Test
    void unCheckedCatch() {
        Service service = new Service();
        service.callCatch();
    }

    @Test
    void unCheckedThrow() {
        Service service = new Service();
        Assertions.assertThrows(MyUnCheckedException.class, () -> service.callThrow());
    }

    // RuntimeException을 상속받기 때문에 언체크 예외가 된다.
    static class MyUnCheckedException extends RuntimeException {

        public MyUnCheckedException(String message) {
            super(message);
        }
    }

    /**
     * 필요한 경우 예외를 잡아 처리하면 된다.
     */
    static class Service {

        Repository repo = new Repository();

        public void callCatch() {
            try {
                repo.call();
            }
            catch (MyUnCheckedException e) {
                log.error("예외처리 >>> {}", e.getMessage(), e);
            }
        }

        // Exception을 그냥 밖으로 던진다. throws를 해도 상관은 없다.
        public void callThrow() {
            repo.call();
        }
    }

    static class Repository {

        public void call() {
            throw new MyUnCheckedException("Repository Exception");
        }
    }
}
