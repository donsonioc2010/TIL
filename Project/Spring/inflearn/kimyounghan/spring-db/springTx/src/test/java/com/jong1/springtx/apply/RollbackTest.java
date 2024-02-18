package com.jong1.springtx.apply;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class RollbackTest {

    @Autowired
    private RollbackService service;

    @Test
    void runtimeException() {
        Assertions.assertThrows(RuntimeException.class, () -> service.runtimeException());
    }

    @Test
    void checkedException() {
        Assertions.assertThrows(MyException.class, () -> service.checkedException());
    }

    @Test
    void rollbackFor() {
        Assertions.assertThrows(MyException.class, () -> service.rollbackFor());
    }

    @TestConfiguration
    static class RollbackTestConfig {
        @Bean
        public RollbackService rollbackService() {
            return new RollbackService();
        }
    }

    @Slf4j
    static class RollbackService {
        // 런타임 예외 발생 : Rollback
        @Transactional
        public void runtimeException() {
            log.info("RollbackService.runtimeException");
            throw new RuntimeException("RollbackService.runtimeException");
        }

        // 체크 예외 발생 : Commit
        @Transactional
        public void checkedException() throws MyException {
            log.info("RollbackService.checkedException");
            throw new MyException();
        }

        // 체크 예외 RollbackFor 지정 : Rollback
        @Transactional(rollbackFor = MyException.class)
        public void rollbackFor() throws MyException{
            log.info("RollbackService.rollbackFor");
            throw new MyException();
        }
    }

    static class MyException extends Exception {

    }
}
